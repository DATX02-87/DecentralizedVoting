const {createContext, CryptoFactory} = require('sawtooth-sdk/signing')
const axios = require('axios').default;
const fs = require('fs');

const context = createContext('secp256k1');
const baseUrl = process.env.API_URL_INTERNAL;

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
});

const buildEndElection = electionName => ({
    action: 'END_ELECTION',
    data: JSON.stringify({
        electionName
    })
});

const generateKeys = (amount, saveFiles) => {
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
    if (!saveFiles) {
        console.log('Voter keys');
        console.table(data.map(d => d.privateKey))
        return data;
    }
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
const sleep = (time) => new Promise(res => setTimeout(res, time));
const main = async () => {
    const initPayload = buildInitPayload(publicKey);
    await sendPayload(initPayload, signer);
    
    console.log('Admin private key:', privKeyHex);
    const voters = generateKeys(20);
    const electionName = 'Demo val 1';
    await sleep(1000);
    await sendPayload(buildAddElectionPayload(
        electionName,
        voters.map(d => d.publicKey),
        [publicKey]
    ), signer);
    const buildAddCandidatePayload = buildAddCandidateToElectionPayload(electionName);
    // const buildCastVote = buildCastVoteOnElection(electionName)
    await sendPayload(buildAddCandidatePayload('Kandidat 1'), signer)
    await sendPayload(buildAddCandidatePayload('Kandidat 2'), signer)
    await sendPayload(buildAddCandidatePayload('Kandidat 3'), signer)
    await sendPayload(buildAddCandidatePayload('Kandidat 4'), signer)
    // await sendPayload(buildCastVote('kalle'), signer);
    // await sendPayload(buildEndElection(electionName), signer);
}
main();