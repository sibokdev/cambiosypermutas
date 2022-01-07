package app.cambiosypermutas.cliente.clients;


import java.io.IOException;

import app.cambiosypermutas.cliente.models.geocode.Geocode;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NandoVelazquez on 8/16/16.
 */
public class GoogleMapsClient {


    private static GoogleMapsClient INSTANCE_CLIENT = null;
    private static APIGoogleMaps API_CLIENT = null;

    private final String baseURL = "https://maps.googleapis.com/maps/api/";
    private OkHttpClient httpClient;
    private OkHttpClient.Builder httpBuilder;

    public interface APIGoogleMaps {
        @GET("geocode/json?")
        Call<Geocode> geocodeAddress(@Query("address") String address, @Query("key") String googleMapsKey);

        @GET("geocode/json?")
        Call<Geocode> geocodeLocation(@Query("latlng") String location, @Query("key") String googleMapsKey);
    }

    public GoogleMapsClient() {
        Retrofit retrofit = setUpClient();
        API_CLIENT = retrofit.create(APIGoogleMaps.class);
    }

    private Retrofit setUpClient() {
        httpBuilder = new OkHttpClient.Builder();

        addTokenInterceptor();
        addLoggingInterceptor();

        httpClient = httpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit;
    }

    public static GoogleMapsClient getInstance() {
        if (INSTANCE_CLIENT == null)
            INSTANCE_CLIENT = new GoogleMapsClient();

        return INSTANCE_CLIENT;
    }

    public APIGoogleMaps getAPI() {
        return API_CLIENT;
    }



    private void addLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

//        if (APILibSettings.isInDevelopment())
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        else
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpBuilder.addInterceptor(logging);
    }

    private void addTokenInterceptor() {
        Interceptor tokenInter = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        //.addHeader("Authorization", token)
                        .addHeader("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        };

        httpBuilder.addInterceptor(tokenInter);
    }

}
