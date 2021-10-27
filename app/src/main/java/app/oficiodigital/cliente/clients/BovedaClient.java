package app.oficiodigital.cliente.clients;


import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.models.Busqueda;
import app.oficiodigital.cliente.models.Datos;
import app.oficiodigital.cliente.models.Direccion;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.Request.DatosSchool;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.Respuestas;
import app.oficiodigital.cliente.models.Solicitudes;
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
 * Created by Roberasd on 29/11/16.
 */
public class BovedaClient {

    private static BovedaClient INSTANCE_CLIENT = null;
    private static APIBovedaClient API_CLIENT = null;
    private final String BASE_URL = "https://drber.com.mx/api/v1/";
    private OkHttpClient mHttpClient;
    private OkHttpClient.Builder mHttpBuilder;

    public interface APIBovedaClient {
        @POST("courier/login")
        @FormUrlEncoded
        Call<Responses> login(@FieldMap HashMap<String, String> params);

        @POST("courier/validate")
        @FormUrlEncoded
        Call<Responses> validate(@FieldMap HashMap<String, String> params);

        @POST("courier/generateInvitationCode")
        @FormUrlEncoded
        Call<Responses> generate(@FieldMap HashMap<String, String> params);

        @POST("courier/updatePassword")
        @FormUrlEncoded
        Call<Responses> updatePass(@FieldMap HashMap<String, String> params);

        @GET("client/getSolicitud{phoneCliente}")
        Call<List<Solicitudes>> getSolicitudes(@Path("phoneCliente") String phone);

        @POST("courier/validateEmail")


        Call<Responses> validateEmail(@FieldMap HashMap<String, String> params);
        @GET("query/info_cp/{codigo}?token=c213f0c9-2d13-4682-9a07-a889abc7c877")
        Call<List<Ejemplo>> getcode(@Path("codigo") String getCode);

        @GET("client/getCP{codigo}")
        Call<List<Ejemplo>> getCP(@Path("codigo") String codigo);


        @GET("courier/Respuestas{phone}")
        Call<List<Respuestas>> getRespuestas(@Path("phone") String phone);

        @POST("courier/register")
        @FormUrlEncoded
        Call<Responses> register(@FieldMap HashMap<String, String> params);

        @POST("courier/reenviarCode")
        @FormUrlEncoded
        Call<Responses> reenviar(@FieldMap HashMap<String, String> params);

        @POST("courier/RecoverPhone")
        @FormUrlEncoded
        Call<Responses> recover(@FieldMap HashMap<String, String> params);

        @GET("courier/getDatos{phone}")
        Call<List<Datos>> getDatos(@Path("phone") String phone);

        @GET("client/Address{id}")
        Call<List<Direccion>> getDirrecion(@Path("id") String id);

        @GET("client/DataSchool{phone}")
        Call<List<DatosSchool>> getDataSchool(@Path("phone") String phone);
//____________________



        @POST("client/updateStatusPago")
        @FormUrlEncoded
        Call<Responses> updatePago(@FieldMap HashMap<String, String> params);

        @POST("client/updateEmail")
        @FormUrlEncoded
        Call<Responses> updateEmail(@FieldMap HashMap<String, String> params);

        @POST("client/updatePhone")
        @FormUrlEncoded
        Call<Responses> updatePhone(@FieldMap HashMap<String, String> params);

        @POST("client/insertPhone2{id}")
        @FormUrlEncoded
        Call<Responses> insertPhone(@Path("id") String id, @FieldMap HashMap<String, String> params);

        @POST("client/updateAdress{id}")
        @FormUrlEncoded
        Call<Responses> updateAddress(@Path("id") String id, @FieldMap HashMap<String, String> params);

        @POST("client/PhotoPerfil{phone}")
        @FormUrlEncoded
        Call<Responses> photoPerfil(@Path("phone") String id, @FieldMap HashMap<String, String> params);

        @GET("client/getOficios")
        Call<List<Busqueda>> getOficios();

        @POST("client/registroUsers")
        @FormUrlEncoded
        Call<Responses> registrarClientes(@FieldMap HashMap<String, String> params);

        @POST("client/solicitud")
        @FormUrlEncoded
        Call<Responses> solicitud(@FieldMap HashMap<String, String> params1);

        @POST("courier/StatusPago")
        @FormUrlEncoded
        Call<Responses> statusPago(@FieldMap HashMap<String, String> params);

        @POST("courier/updateSolicitud")
        @FormUrlEncoded
        Call<Responses> Getsolicitud(@FieldMap HashMap<String, String> params1);

        @POST("client/registroDataSchool")
        @FormUrlEncoded
        Call<Responses> registroEscuela(@FieldMap HashMap<String, String> params);

        @POST("client/registroIntereses")
        @FormUrlEncoded
        Call<Responses> registroIntereses(@FieldMap HashMap<String, String> params);


    }


    public BovedaClient(){
        Retrofit retrofit = setUpClient();
        API_CLIENT = retrofit.create(APIBovedaClient.class);
    }

    public static BovedaClient getInstanceClient(){
        if (INSTANCE_CLIENT == null)
            INSTANCE_CLIENT = new BovedaClient();

        return INSTANCE_CLIENT;
    }

    public APIBovedaClient getApiClient(){
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
                    //.addHeader("Authorization", token)
                    .addHeader("Accept", "application/json")
                    .method(original.method(), original.body());

            Request request = requestBuilder.build();

            return chain.proceed(request);
        };

        mHttpBuilder.addInterceptor(tokenInter);
    }
}
