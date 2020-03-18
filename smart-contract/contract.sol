pragma solidity ^0.5.16;

contract election{

    struct Candidate{
        string name;
        uint votesReceived;
    }
    struct Voter{
        bool allowedToVote;
        bool hasVoted;
        uint vote;
    }

    address payable public contractOwner;
    string public electionName;
    uint public totalVotes;

    mapping(address => Voter) public voters;
    Candidate[] public candidates;

    modifier isOwner() {
        require(msg.sender == contractOwner);
        _;
    }
    constructor() public {
        contractOwner = msg.sender;
        electionName = "Valet";
    }
    function addCandidate(string memory _name) isOwner public {
        candidates.push(Candidate(_name, 0));
    }

    function getTotalCandidates() public view returns(uint){
        return candidates.length;
    }

    function authorize(address _voter) isOwner public {
        voters[_voter].allowedToVote = true;
    }

    function vote(uint _voteIndex) public {
        require(!voters[msg.sender].hasVoted);
        require(voters[msg.sender].allowedToVote);
        
        voters[msg.sender].vote = _voteIndex;
        voters[msg.sender].hasVoted = true;

        candidates[_voteIndex].votesReceived += 1;
        totalVotes += 1;
    }

    function stopElection() isOwner public{
        selfdestruct(contractOwner);
    }
}