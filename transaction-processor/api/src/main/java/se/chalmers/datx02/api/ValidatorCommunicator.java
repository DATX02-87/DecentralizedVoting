package se.chalmers.datx02.api;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ValidatorCommunicator {
    private RestAPI service;
    private Retrofit retrofit;
    private RequestBody body;
    private static String validatorAddress;
    private static String stateAddress;
    private static ValidatorCommunicator instance;

    private ValidatorCommunicator() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(validatorAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(RestAPI.class);
    }
    public static void init(String address_to_validator, String address_to_state){
        validatorAddress = address_to_validator;
        stateAddress =  address_to_state;
    }

    public static ValidatorCommunicator getInstance(){
        if (instance == null)
             instance = new ValidatorCommunicator();
        return instance;
    }

    public void postBatchList(byte[] batchList){
        this.body = RequestBody.create(MediaType.parse("application/octet-stream"), batchList);
        callValidator();

    }

    public byte[] getState() {
        return service.getState().request().body().toString().getBytes();
    }

   private void callValidator(){

       Call call = service.postTransaction(body);
       call.enqueue(new Callback(){

           @Override
           public void onResponse(@NotNull Call call, @NotNull Response response) {
               if(response.body() != null){
                   System.out.printf("Response: %s", response.body().toString());
               }
               else{
                   System.out.printf("Response: %s", response.toString());
               }
           }
           @Override
           public void onFailure(@NotNull Call call, @NotNull Throwable throwable) {
               System.out.printf("Response: %s", throwable.toString());
               call.cancel();
           }
       });
   }
}
