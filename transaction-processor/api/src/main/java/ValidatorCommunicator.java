import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sawtooth.sdk.protobuf.Batch;
import sawtooth.sdk.protobuf.BatchList;

class ValidatorCommunicator {
    private RestAPI service;
    private Retrofit retrofit;
    private RequestBody body;

    ValidatorCommunicator(byte[] batchListBytes) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://rest.api.domain/batches")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(RestAPI.class);
        this.body = RequestBody.create(MediaType.parse("application/octet-stream"), batchListBytes);
    }

   void callValidator(){

       Call call = service.postTransaction(body);
       call.enqueue((Callback) new Callback(){

           @Override
           public void onResponse(@NotNull Call call, @NotNull Response response) {
               if(response.body() != null){
                   System.out.printf("Response", response.body().toString());
               }
               else{
                   System.out.printf("Response", response.toString());
               }
           }
           @Override
           public void onFailure(@NotNull Call call, @NotNull Throwable throwable) {
               System.out.printf("Response", throwable.toString());
               call.cancel();
           }
       });
   }
}
