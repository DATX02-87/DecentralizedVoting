const {createContext, CryptoFactory} = require('sawtooth-sdk/signing')
const axios = require('axios').default;
const fs = require('fs');

const context = createContext('secp256k1');
const baseUrl = 'http://decentralizedvoting-api.vltmn.io';

const privateKey = context.newRandomPrivateKey();
const privKeyHex = privateKey.asHex();
const signer = new CryptoFactory(context).newSigner(privateKey);
const publicKey = context.getPublicKey(privateKey).asHex();

const buildInitPayload = publicKey => ({
    action: 'INIT',
    data: JSON.stringify({
        masterAdmins: [publicKey]
    })
})
const buildAddElectionPayload = (electionName, voters, admins) => ({
    action: 'ADD_ELECTION',
    data: JSON.stringify({
        name: electionName,
        allowedVoters: voters,
        admins
    })
});

const buildAddCandidateToElectionPayload = electionName => candidateName => ({
    action: 'ADD_CANDIDATE',
    data: JSON.stringify({
        candidate: candidateName,
        electionName
    })
});

const buildCastVoteOnElection = electionName => candidateName => ({
    action: 'CAST_VOTE',
    data: JSON.stringify({
        candidate: candidateName,
        electionName: electionName
    })
})

const generateKeys = (amount) => {
    const data = new Array(amount).fill()
        .map((_, i) => {
            const privateKey = context.newRandomPrivateKey();
            const privateKeyHex = privateKey.asHex();
            const publicKey = context.getPublicKey(privateKey).asHex();
            return {
                index: i,
                privateKey: privateKeyHex,
                publicKey
            };
        });
    
    const fileContent = data.map(e => 
`${e.index}
${e.privateKey}`)
        .reduce((acc, curr) => 
`${acc}
${curr}`, '');
    const dateStr = (new Date()).toISOString();
    fs.writeFileSync('./keys-' + dateStr, `admin
${privateKey.asHex()}
` + fileContent)
    return data;
}

const sendPayload = async (payload, signer) => {
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
    }
    try {
        const transactionResp = await axios.post(baseUrl + '/rest/transaction', transaction);
        console.log(transactionResp.data);
    } catch (e) {
        console.error(e.response.data);
        throw e;
    }
    await new Promise(res => setTimeout(res, 2000));
}
const main = async () => {
    const initPayload = buildInitPayload(publicKey);
    await sendPayload(initPayload, signer);
    const voters = generateKeys(20);
    const elecitonName = 'Demo val';
    const buildAddCandidatePayload = buildAddCandidateToElectionPayload(elecitonName);
    // const buildCastVote = buildCastVoteOnElection(elecitonName)
    await sendPayload(buildAddElectionPayload(
        elecitonName,
        voters.map(d => d.publicKey),
        [publicKey]
    ), signer);
    await sendPayload(buildAddCandidatePayload('kalle'), signer)
    await sendPayload(buildAddCandidatePayload('kula'), signer)
    await sendPayload(buildAddCandidatePayload('markus'), signer)
    // await sendPayload(buildCastVote('kalle'), signer);
}
main();