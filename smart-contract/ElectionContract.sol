pragma solidity >=0.4.22 <0.7.0;

contract ElectionContract {
  
    struct Candidate {
        uint id;
        bytes32 name;
        uint voteCount;
    }
    
     struct Voter {
        address voterAddress;
        bool voted;
        bool isEligible;
       // uint voteID;
    }
    
    /*
    bytes32[] _candidates;
    
    function init() public {
        _candidates.push("Candidate 1");
        _candidates.push("Candidate 2");
    }
    */
    Candidate[] public candidates;
    //address public admin;
    
    mapping (address => Voter) public voters;
    mapping(uint => Candidate) public candidateMap;
    mapping(address => bool) public admins;
    
    uint public numCandidates = 0;
    uint startTime;
    uint endTime;
    
    uint public totalVotes;
    
    modifier restricted() {
        require(admins[msg.sender]);
        _;
    }
    
    constructor (bytes32[] _candidates , address[] _admins, uint _startTime, uint _endTime) public {
        //admins[msg.sender] = true;
        
        require(now < _startTime);
        require(_endTime > _startTime);
        
        
        for (uint i = 0; i < _admins.length; i++) {
            admins[_admins[i]] = true;
        }
        
        for (uint j = 0; j < _candidates.length; j++) {
            addCandidate(_candidates[i]);
        }
        
        startTime = _startTime;
        endTime = (_startTime + _endTime) * 1 hours;
    }
    
    function addCandidate(bytes32 _name) restricted public {
        
        require(admins[msg.sender], "You are not authorized to add a candidate!");
        require(now > startTime);
        //candidates[numCandidates] = Candidate(numCandidates, _name, 0);
        
        candidates.push(Candidate({
                id: numCandidates,
                name: _name,
                voteCount: 0
        }));
        
        numCandidates += 1;
            
    }
    
    
    function validateCandidate(uint _candidateId) public view returns(bool) {
        require(_candidateId < numCandidates, "Candidate does not exist");
    }
    
    function vote (uint _candidateId) public {
        
        // require that they haven't voted before
        require(!voters[msg.sender].voted);
        validateCandidate(_candidateId);
        
        // Check if the timne of election is not over
        require(now > endTime, "The time of election is over");
        
        // record that voter has voted
        voters[msg.sender].voted = true;
        
        // update candidate vote Count
        candidates[_candidateId].voteCount ++;
        totalVotes += 1;

    }
    
    function getCandidateName(uint id) public restricted view returns (bytes32) {
        
        require(now > endTime);
        return candidates[id].name;
    }
    
    function getVoteCount(uint id) public restricted view returns (uint) {
        
        require(now > endTime);
        validateCandidate(id);
        return candidates[id].voteCount;
    }
    
    function end() restricted public {
        if(now < endTime) {
            revert();
        }
        require(admins[msg.sender]);
        selfdestruct(msg.sender);
    }

}
