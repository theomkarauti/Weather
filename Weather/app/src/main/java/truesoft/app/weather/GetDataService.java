package truesoft.app.weather;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("forecast?q=London&appid=59dbf1369e8020e91a40a4bea835732c")
    Call<TempData> getAllPhotos();

//    @GET("forecast?")
//    Call<TempData> getAllPhotos(@Field("q") String q, @Field("appid") String appid);

}
