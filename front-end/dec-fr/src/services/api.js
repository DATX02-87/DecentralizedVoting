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
  // TODO
  return [
    {
      name: 'TestVal',
      active: true,
      hasVoted: false,
      eligible: true,
      candidates: {
        kalle: 12,
        kula: 11,
        petter: 8,
        nicklas: 15,
      },
    },
    {
      name: 'TestVal2',
      active: true,
      hasVoted: true,
      eligible: true,
      candidates: {
        kalle: 12,
        kula: 11,
        petter: 8,
        nicklas: 15,
      },
    },
  ];
};

const getCastResult = async (batchId, transactionId) => {
  return;
};

export const castVote = async (privateKey, electionName, candidateName) => {
  // TODO
  const batchId = '';
  const transactionId = '';
  return {
    transactionId,
    batchId,
    result: getCastResult(batchId, transactionId),
  };
};
