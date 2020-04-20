const { createContext, CryptoFactory } = require('sawtooth-sdk/signing')
const { Secp256k1PrivateKey } = require('sawtooth-sdk/signing/secp256k1');

const axios = require('axios').default;
const context = createContext('secp256k1');
const baseUrl = 'https://decentralizedvoting-api.vltmn.io';

const getTransactionResult = async (batchId, transactionId) => {
  let status = '';
  while (status !== 'INVALID' && status !== 'COMMITTED') {
    const { data } = await axios.get(`${baseUrl}/rest/transaction/${batchId}/status`);
    await new Promise((res) => setTimeout(res, 100));
    status = data.status;
  }

  if (status === 'INVALID') {
    throw new Error('Transaction invalid');
  }
  return;
};

const sendPayload = async (payload, signer) => {
  const publicKey = signer.getPublicKey().asHex();
  const response = await axios.post(baseUrl + '/rpc/get-transaction-data-to-sign', {
      transactionPayload: payload,
      publicKey
  });
  const toSign = response.data.dataToSign;
  const signature = signer.sign(Buffer.from(toSign, 'base64'));
  const transaction = {
      signature,
      header: toSign,
      payload
  };
  try {
      const transactionResp = await axios.post(baseUrl + '/rest/transaction', transaction);
      return {
        transactionId: transactionResp.data.transactionId,
        batchId: transactionResp.data.batchId
      }
  } catch (e) {
      console.error(e.response.data);
      throw e;
  }
}

const buildCastVote = (candidateName, electionName) => ({
  action: 'CAST_VOTE',
  data: JSON.stringify({
      candidate: candidateName,
      electionName: electionName
  })
})

export const castVote = async (privateKey, electionName, candidateName) => {
  const privateKeyObj = Secp256k1PrivateKey.fromHex(privateKey);
  const signer = new CryptoFactory(context).newSigner(privateKeyObj);
  const transaction = buildCastVote(candidateName, electionName);
  const resp = await sendPayload(transaction, signer);
  // do this asynchronous if the blockchain is too slow. it is not currently but might be in the future
  await getTransactionResult(resp.batchId, resp.transactionId);
  return resp;
};

export const getEligibleElections = async (privateKey) => {
  const allElections = await getElections(privateKey);
  return allElections.filter((e) => e.eligible);
};

export const getElection = async (name, privateKey) => {
  const elections = await getEligibleElections(privateKey);
  return elections.filter((e) => {
    return e.name === name;
  })[0];
};

export const getElections = async (privateKey) => {
  let privateKeyObj;
  try {
    privateKeyObj = Secp256k1PrivateKey.fromHex(privateKey);
  } catch (e) {
    console.log('Invalid key');
    return [];
  }
  const publicKey = context.getPublicKey(privateKeyObj).asHex();
  const { data } = await axios.get(`${baseUrl}/rest/state`);
  return Object.entries(data.elections).map(el => ({
    name: el[0],
    active: el[1].active,
    eligible: new Set(Object.keys(el[1].voters)).has(publicKey),
    hasVoted: Object.entries(el[1].voters)
      .filter(obj => obj[0] === publicKey).some(obj => obj[1]),
    candidates: Object.keys(el[1].candidates)
  }));
};

export const getElectionStatistics = async (electionName) => {
  const { data } = await axios.get(`${baseUrl}/rest/state`);
  const electionEntry = Object.entries(data.elections).filter(e => e[0] === electionName)[0]
  if (!electionEntry) {
    console.error('No election was found in api for ' + electionName);
    return [];
  }
  const election = electionEntry[1];
  return Object.entries(election.candidates).map(entry => ({
    name: entry[0],
    votes: entry[1]
  }));
}