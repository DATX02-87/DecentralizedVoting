import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import { Link } from 'react-router-dom';
import VotationStats from './VotationStats';

const SelectCandidateForm = ({candidates, selectedCandidate, onChange, onSubmit, castingVote}) => (
    <Form onSubmit={onSubmit}>
        <Form.Group controlId="selectCandidateForm">
            <Form.Label>Please pick an option below</Form.Label>
            <Form.Control as="select" value={selectedCandidate} custom onChange={onChange} disabled={castingVote}>
                <option></option>
                {candidates.sort().map((candidate) => (
                    <option key={candidate}>{candidate}</option>
                ))}
            </Form.Control>
        </Form.Group>
        
        <Button variant='primary' type='submit' disabled={selectedCandidate === '' || !selectedCandidate || castingVote}>
            Cast vote
        </Button>
        
    </Form>
);
const VotationDisplay = ({name, active, hasVoted, candidates, castingVote, onVote, eligible, admin, endingVotation, onEndVotation}) => {
    const [selectedCandidate, setSelectedCandidate] = useState(undefined);
    const onChange = (evt) => {
        setSelectedCandidate(evt.target.value);
    }
    const onSubmit = (evt) => {
        evt.preventDefault();
        if (!selectedCandidate) {
            throw new Error('A candidate should be selected');
        }
        onVote(selectedCandidate);
    }

    const onEndElection = (evt) => {
        evt.preventDefault();
        onEndVotation();
    }
    return (
    <Card>
        <Card.Body>
            <Card.Title>{name}</Card.Title>
            {active ? (
                <Card.Subtitle className="text-muted">Currently active</Card.Subtitle>
            ) : (
                <Card.Subtitle className="text-muted">Ended</Card.Subtitle>
            )}
            {active ? 
                eligible ? 
                    hasVoted ?
                        <Card.Text>
                            You have successfully voted in this votation, results will be posted after the end of this votation.
                        </Card.Text> 
                        :
                        <SelectCandidateForm
                        candidates={candidates}
                        selectedCandidate={selectedCandidate}
                        onChange={onChange}
                        onSubmit={onSubmit}
                        castingVote={castingVote}
                        />
                    : 
                    admin ? 
                        <Button variant='primary' type='button' disabled={endingVotation} onClick={onEndElection}>
                            End votation
                        </Button> 
                        :
                        <Card.Text>Invalid state</Card.Text>
                :
                <VotationStats votationName={name} />
            }
            <Link to='/' className='btn btn-light'>Back</Link>
            {/* {hasVoted ? (
            <>
                {active ? (
                <Card.Text>
                    You have successfully voted in this votation, results will be posted after the end of this votation.
                </Card.Text>
                ) : (
                    <VotationStats votationName={name} />
                )}
                
                <Link to='/' className='btn btn-light'>Back</Link>
            </>
            ) : (
            <SelectCandidateForm
                candidates={candidates}
                selectedCandidate={selectedCandidate}
                onChange={onChange}
                onSubmit={onSubmit}
                castingVote={castingVote}
            />
            )} */}
        </Card.Body>
    </Card>
    );
};

VotationDisplay.propTypes = {
    name: PropTypes.string.isRequired,
    active: PropTypes.bool.isRequired,
    hasVoted: PropTypes.bool.isRequired,
    candidates: PropTypes.array.isRequired,
    castingVote: PropTypes.bool.isRequired,
    onVote: PropTypes.func.isRequired
};

export default VotationDisplay;