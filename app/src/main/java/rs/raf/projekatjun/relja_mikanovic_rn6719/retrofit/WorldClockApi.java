package rs.raf.projekatjun.relja_mikanovic_rn6719.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WorldClockApi {

    @GET("/api/timezone/Europe/{city}")
    Call<EasternStandardTimeModel> getEasternStandardTimeForCity(@Path(value = "city") String myCity);


}
