package app.cambiosypermutas.cliente.clients;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roberasd on 15/11/16.
 */

public class DOXClient {

    public static final String URL_IMAGES_BOVEDA = "https://cambiosypermutas.com.mx/api/v1/";
    private static final String BASE_URL = "https://cambiosypermutas.com.mx/api/v1/";
    private static DOXClient INSTANCE_CLIENT = null;
    private static APIDOXClient API_CLIENT = null;
    private OkHttpClient.Builder mHttpBuilder;

    public DOXClient() {
        Retrofit retrofit = setUpClient();
        API_CLIENT = retrofit.create(APIDOXClient.class);
    }

    public static DOXClient getInstanceClient() {
        if (INSTANCE_CLIENT == null)
            INSTANCE_CLIENT = new DOXClient();

        return INSTANCE_CLIENT;
    }

    public APIDOXClient getApiClient() {
        return API_CLIENT;
    }

    private Retrofit setUpClient() {
        mHttpBuilder = new OkHttpClient.Builder();

        addTokenInterceptor();
        addLoggingInterceptor();

        OkHttpClient httpClient = mHttpBuilder.build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
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
