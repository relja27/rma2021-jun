package rs.raf.projekatjun.relja_mikanovic_rn6719.retrofit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorldClockService {

    private WorldClockApi worldClockApi;

    private MutableLiveData<EasternStandardTimeModel> easternStandardTime = new MutableLiveData<>();

    private static final String BASE_URL = "https://worldtimeapi.org";

    public WorldClockService() {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(logging);

        OkHttpClient okHttpClient = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        worldClockApi = retrofit.create(WorldClockApi.class);
    }

    public LiveData<EasternStandardTimeModel> getEasternStandardTime() {
        return easternStandardTime;
    }

    public void invokeCityService(String city) {

        Call<EasternStandardTimeModel> call = worldClockApi.getEasternStandardTimeForCity(city);

        call.enqueue(new Callback<EasternStandardTimeModel>() {
            @Override
            public void onResponse(
                    Call<EasternStandardTimeModel> call,
                    Response<EasternStandardTimeModel> response) {

                if (response.isSuccessful()) {
                   easternStandardTime.setValue(response.body());
                }
            }

            @Override
            public void onFailure(
                    Call<EasternStandardTimeModel> call,
                    Throwable t) {

            }
        });
    }





}
