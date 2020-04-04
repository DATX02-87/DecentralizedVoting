

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

import sawtooth.sdk.protobuf.TpProcessRequest;
import sawtooth.sdk.protobuf.TransactionHeader;
import sawtooth.sdk.processor.TransactionHandler;
import sawtooth.sdk.processor.Context;
import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.processor.exceptions.InternalError;
import sawtooth.sdk.processor.exceptions.InvalidTransactionException;


public class VotingHandler implements TransactionHandler {

  private final Logger logger = Logger.getLogger(VotingHandler.class.getName());
  private String votingNameSpace;
  private final static String version = "1.0";
  private final static String txFamilyName = "votingSystem";

  /**
   * 
   */
  public VotingHandler() {
    try {
      this.votingNameSpace = Utils.hash512(
        this.transactionFamilyName().getBytes("UTF-8")).substring(0, 6);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      this.votingNameSpace = "";
    }
  }

  @Override
  public String transactionFamilyName() {
    return txFamilyName;
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public Collection<String> getNameSpaces() {
    ArrayList<String> namespaces = new ArrayList<>();
    namespaces.add(this.votingNameSpace);
    return namespaces;
  }

  class TxnPayload {
    final String electionName;
    final String action;
    final String id;

    TxnPayload(String electionName, String action, String id) {
      this.electionName = electionName;
      this.action = action;
      this.id = id;
    }
  }

  class Candidate {
    final String electionName;
    final String name;
    final String id;
    Integer voteCount;

    Candidate(String electionName, String name, String id, int voteCount) {
      this.electionName = electionName;
      this.name = name;
      this.id = id;
      this.voteCount = voteCount;
  }

  class Voter {
    final String electionName;
    final String address;
    boolean hasVoted;

    Voter(String electionName, String address, boolean hasVoted) {
      this.electionName = electionName;
      this.address = address;
      this.hasVoted = false;
  }

  @Override
  public void apply(TpProcessRequest tpRequest, Context context)
      throws InvalidTransactionException, InternalError {
    TxnPayload txnPayload = getUnpackedTransaction(tpRequest);

    TransactionHeader header = tpRequest.getHeader();

    // The transaction signer is the voter
    String voterKey;
    voterKey = header.getSignerPublicKey();

    if (txnPayload.electionName.equals("")) {
      throw new InvalidTransactionException("Name is required");
    }
    
    String action = txnPayload.action;

    switch(action) {
      case "addCandidate":
        String candidateId = txnPayload.id;
        addCandidate(context, action, candidateId, voterKey);
        break;

      case "vote":
        String candidateId = txnPayload.id;
        castVote(context, action, candidateId, voterKey);
        break;

      case "getTotalVoteCount":
        getTotalVoteCount();
        break;

      case "getWinner":
        getWinner();
        break;

      default:
        String error = "Unknown operation " + action;
    }
    
  }

  /**
   * Helper function to retrieve electionName, action, and id from transaction request.
   */
  private TxnPayload getUnpackedTransaction(TpProcessRequest tpRequest)
      throws InvalidTransactionException {

    String payload =  tpRequest.getPayload().toStringUtf8();
    ArrayList<String> payloadList = new ArrayList<>(Arrays.asList(payload.split(",")));

    if (payloadList.size() > 3) {
      throw new InvalidTransactionException("Invalid payload serialization");
    }

    while (payloadList.size() < 3) {
      payloadList.add("");
    }

    return new TxnPayload(payloadList.get(0), payloadList.get(1), payloadList.get(2));
  }


  /*
   * TODO: check if the signer is authorized to add a candidate
   */

  private void addCandidate(Context context, String action, String candidateID, String key) {
    String candidateAddress = getCandidateAddress(candidateID);
 
    Map<String, ByteString> currentEntry = context.getState(Collections.singletonList(candidateAddress));

    String vCount = currentEntry.get(candidateAddress);

    if(!vCount.isEmpty()) {
      throw new InvalidTransactionException("Candidate already exists");
    }

    Integer count = 0;

    Map.Entry<String, ByteString> entry = new AbstractMap.SimpleEntry<String,ByteString>(candidateAddress,
          ByteString.copyFromUtf8(count.toString()));

    Collection<Map.Entry<String, ByteString>> newEntry = Collections.singletonList(entry);
    logger.info("Adding Candidate: " + candidateAddress);

    context.setState(newEntry);
  }


  private void castVote(context, action, candidateID, voterKey) {
    String candidateAddress = getCandidateAddress(candidateID);
    Map<String, ByteString> candidateEntry = context.getState(Collections.singletonList(candidateAddress));

    String vCount = candidateEntry.get(candidateAddress);

    if(vCount.isEmpty()) {
      throw new InvalidTransactionException("Candidate does not exist");
    }
    
    String voterAddress = getVoterAddress(voterKey);
    Map<String, ByteString> voterEntry = context.getState(Collections.singletonList(voterAddress));

    String candidate = voterEntry.get(voterAddress);

    if(!candidate.isEmpty()) {
      throw new InvalidTransactionException("Voter has already voted");
    }

    Integer voteCount = Integer.valueOf(vCount);
    voteCount = voteCount + 1;

    Map.Entry<String, ByteString> cEntry = new AbstractMap.SimpleEntry<String,ByteString>(candidateAddress,
          ByteString.copyFromUtf8(voteCount.toString()));

    Collection<Map.Entry<String, ByteString>> newCEntry = Collections.singletonList(cEntry);
    logger.info("voteCount updated: " + voteCount);

    context.setState(newCEntry);

    Map.Entry<String, ByteString> vEntry = new AbstractMap.SimpleEntry<String,ByteString>(voterAddress,
          ByteString.copyFromUtf8(candidateID));

    Collection<Map.Entry<String, ByteString>> newVEntry = Collections.singletonList(vEntry);

    context.setState(newVEntry);

  }
 

  /*
   * TODO
   */
  private void getTotalVoteCount() {

  }

  /*
   * TODO
   */
  private void getWinner() {
    
  }
  }

  /*
   * Generate unique wallet key from the wallet namespace
   * and user voter public key
   */
  private String getVoterAddress(String voterKey) {
    return Utils.hash512(txFamilyName.getBytes("UTF-8")).substring(0, 6)
        + Utils.hash512(voterKey.getBytes("UTF-8")).substring(0, 64);
  }

  private String getCandidateAddress(String candidateID) {
    return Utils.hash512(txFamilyName.getBytes("UTF-8")).substring(0, 6)
        + Utils.hash512(candidateID.getBytes("UTF-8")).substring(0, 64);
  }

}