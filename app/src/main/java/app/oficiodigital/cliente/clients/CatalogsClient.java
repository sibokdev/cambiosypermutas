package app.oficiodigital.cliente.clients;

import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.models.Responses;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by asuarezr.
 */
public class CatalogsClient {

    private static CatalogsClient INSTANCE_CLIENT = null;
    private static APICatalogsClient API_CLIENT = null;
    private final String BASE_URL = "https://drber.com.mx/";
    private OkHttpClient mHttpClient;
    private OkHttpClient.Builder mHttpBuilder;

    public interface APICatalogsClient {
        @POST("messages/work/particularRequest")
        @FormUrlEncoded
        Call<Responses> notificaciones(@FieldMap HashMap<String, String> params);

        @POST("messages/work/particularRequestSuccess")
        @FormUrlEncoded
        Call<Responses> notificaSuccess(@FieldMap HashMap<String, String> params);

        @POST("messages/work/particularRequestFaile")
        @FormUrlEncoded
        Call<Responses> notificaFaile(@FieldMap HashMap<String, String> params);

        @POST("messages/work/sendMessageToAllProvideersCotizacion")
        @FormUrlEncoded
        Call<Responses> notificacionesCoti(@FieldMap HashMap<String, String> params);
    }


    public CatalogsClient(){
        Retrofit retrofit = setUpClient();
        API_CLIENT = retrofit.create(APICatalogsClient.class);
    }

    public static CatalogsClient getInstanceClient(){
        if (INSTANCE_CLIENT == null)
            INSTANCE_CLIENT = new CatalogsClient();

        return INSTANCE_CLIENT;
    }

    public APICatalogsClient getApiClient(){
        return API_CLIENT;
    }

    private Retrofit setUpClient() {
        mHttpBuilder = new OkHttpClient.Builder();

        addTokenInterceptor();
        addLoggingInterceptor();

        mHttpClient = mHttpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mHttpClient)
                .build();

        return retrofit;
    }

    private void addLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        mHttpBuilder.addInterceptor(logging);
    }

    private void addTokenInterceptor() {
        Interceptor tokenInter = chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();

            return chain.proceed(request);
        };

        mHttpBuilder.addInterceptor(tokenInter);
    }
}
