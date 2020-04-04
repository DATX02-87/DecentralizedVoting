import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import sawtooth.sdk.protobuf.BatchList;

public interface RestAPI {
    @POST("/batches")
    Call<byte[]> postTransaction(@Body RequestBody payload);
}
