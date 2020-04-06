package se.chalmers.datx02.transaction_processor;



import com.google.protobuf.ByteString;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import sawtooth.sdk.protobuf.TpProcessRequest;
import sawtooth.sdk.protobuf.TransactionHeader;
import sawtooth.sdk.processor.TransactionHandler;
import sawtooth.sdk.processor.State;
import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.processor.exceptions.InternalError;
import sawtooth.sdk.processor.exceptions.InvalidTransactionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.chalmers.datx02.model.Transaction;
import se.chalmers.datx02.model.TransactionPayload;
import se.chalmers.datx02.model.exception.InvalidStateException;
import se.chalmers.datx02.model.exception.ReducerException;

import se.chalmers.datx02.model.state.GlobalState;

import se.chalmers.datx02.model.Adressing;
import se.chalmers.datx02.model.DataUtil;
import se.chalmers.datx02.model.Reducer;

public class VotingHandler implements TransactionHandler {

  //private final Logger logger = Logger.getLogger(VotingHandler.class.getName());
  private String votingNameSpace;
  private final static String version = Adressing.FAMILY_VERSION;
  private final static String familyName = Adressing.FAMILY_NAME;
  private final String masterStateName;

  public VotingHandler(String masterStateName) {
      this.masterStateName = masterStateName;
      this.votingNameSpace = Utils.hash512(
        this.transactionFamilyName().getBytes(StandardCharsets.UTF_8)).substring(0, 6);

  }

  @Override
  public String transactionFamilyName() {
    return familyName;
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


  @Override
  public void apply(TpProcessRequest tpRequest, State context)
      throws InvalidTransactionException, InternalError {
	  
	  
	  Transaction transaction = getTransaction(tpRequest);
	  String address = Adressing.makeMasterAddress(masterStateName);
	  
	  
	  String entry = context.getState(Collections.singletonList(address)
		      ).get(address).toStringUtf8();
	  
	  if(entry.isEmpty()) {
		  throw new InvalidTransactionException("The wallet associated with the user key is not found");
	  }
	  GlobalState currentState = DataUtil.StringToGlobalState(entry);
	  
	  GlobalState newState = null;
	  try {
		  newState = Reducer.applyTransaction(transaction, currentState);
	  } catch (InvalidStateException e) {
			e.printStackTrace();
	  } catch (ReducerException e) {
			e.printStackTrace();
	  }
	  
	  updateStateData(newState, context, address);
  }
  
  /*
   * Helper function to update the state
   */
  private void updateStateData(GlobalState newState, State context, String address) throws InternalError, InvalidTransactionException {
	  /*
	  String updatedState = null;
	  try {
		updatedState = new ObjectMapper().writeValueAsString(newState);
	  } catch (JsonProcessingException e) {
		  e.printStackTrace();
	  }
	  */
	  
	  String updatedState = DataUtil.GlobalStateToString(newState);
	  Map.Entry<String, ByteString> entry = new AbstractMap.SimpleEntry<String,ByteString>(address,
			  	ByteString.copyFromUtf8(updatedState));

	  Collection<Map.Entry<String, ByteString>> newEntry = Collections.singletonList(entry);
	  
	  Collection<String> addresses = context.setState(newEntry);
	  
	  if(addresses.isEmpty()) {
		  throw new InternalError("State error");
	  }
}

  /*
   * Helper function to retrieve the transaction.
   */

  private Transaction getTransaction(TpProcessRequest processRequest)
      throws InvalidTransactionException {
	  
	  String payload = processRequest.getPayload().toStringUtf8();
	  TransactionHeader header = processRequest.getHeader();
	  String submitter = header.getSignerPublicKey();
	  
	  TransactionPayload transactionPayload = DataUtil.StringToTransactionPayload(payload);
      Transaction transaction = new Transaction(transactionPayload, submitter);
	  
	  return transaction;
  }

}