export const getEligibleElections = async (privateKey) => {
    const allElections = await getElections(privateKey);
    return allElections.filter(e => e.eligible);
}
export const getElections = async (privateKey) => {
    // TODO
    return [
        {
            name: 'Test val',
            active: true,
            hasVoted: false,
            eligible: true,
            candidates: {
                kalle: 12,
                kula: 11,
                petter: 8,
                nicklas: 15
            }
        },
        {
            name: 'Test val 2',
            active: true,
            hasVoted: true,
            eligible: true,
            candidates: {
                kalle: 12,
                kula: 11,
                petter: 8,
                nicklas: 15
            }
        }
    ];
}

const getCastResult = async (batchId, transactionId) => {
    return;
}

export const castVote = async (privateKey, electionName, candidateName) => {
    // TODO
    const batchId = '';
    const transactionId = '';
    return {
        transactionId,
        batchId,
        result: getCastResult(batchId, transactionId)
    };
}




