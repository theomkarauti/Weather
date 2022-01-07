package truesoft.app.weather.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import truesoft.app.weather.model.TempData;

public interface GetDataService {


    @GET("forecast?")
    Call<TempData> getAllPhotos(@Query("q") String q, @Query("appid") String appid);

}
