package app.oficiodigital.cliente.clients;

import android.location.Address;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.models.AddService;
import app.oficiodigital.cliente.models.Busqueda;
import app.oficiodigital.cliente.models.CreditCard;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.Paypal;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.Tarjetas;
import app.oficiodigital.cliente.models.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by roberasd on 21/03/17.
 */

public interface APIDOXClient {
    @POST("client/login")
    @FormUrlEncoded
    Call<Responses> login(@FieldMap HashMap<String, String> params);

    @POST("client/contract")
    Call<Responses> contract(@Body User user);

    @GET("query/info_cp/{codigo}?token=a6425d19-2a37-455f-9914-640c316a37b0")
    Call<List<Ejemplo>> getcode(@Path("codigo") String getCode);

    @POST("courier/register")
    @FormUrlEncoded
    Call<Responses> register(@FieldMap HashMap<String, String> params);

    @PUT("client/{client_id}/completeData")
    Call<Responses> completeData(@Path("client_id") String clientId,
                                 @Body User user,
                                 @Header("Authorization") String authorization);

    @DELETE("client/deleteDocument/{document_id}")
    Call<Responses> deleteDocument(@Path("document_id") String documentId,
                                   @Header("Authorization") String authorization);

    @POST("client/addDocument")
    @Multipart
    Call<Responses> addDocument(@Part("alias") RequestBody alias,
                                @Part("type") RequestBody type,
                                @Part("subtype") RequestBody subtype,
                                @Part("location") RequestBody location,
                                @Part("expedition") RequestBody expedition,
                                @Part("expiration") RequestBody expiration,
                                @Part MultipartBody.Part picture,
                                @Part("notes") RequestBody notes,
                                @Header("Authorization") String authorization);

    @GET("client/documents")
    Call<Responses> getDocuments(@Header("Authorization") String authorization);

    @PUT("client/updateDocument/{document_id}")
    Call<Responses> updateDocument(@Path("document_id") String documentId,
                                   @Body Document document,
                                   @Header("Authorization") String authorization);

    @POST("client/updatePictureDocument/{document_id}")
    @Multipart
    Call<Responses> updateDocumentPhoto(@Path("document_id") String documentId,
                                        @Part MultipartBody.Part picture,
                                        @Header("Authorization") String authorization);

    @GET("client/documentMovements/{document_id}")
    Call<Responses> documentRecord(@Path("document_id") String documentId,
                                   @Header("Authorization") String authorization);

    @POST("client/addService")
    Call<Responses> addService(@Body AddService service,
                               @Header("Authorization") String authorization);

    @GET("client/services")
    Call<Responses> getServices(@Header("Authorization") String authorization);

    @PUT("client/cancelService/{service_id}")
    Call<Responses> cancelService(@Path("service_id") String serviceId,
                                  @Header("Authorization") String authorization);

    @GET("client/getReferreds")
    Call<Responses> getReferredFriends(@Header("Authorization") String authorization);

    @POST("client/addReferreds")
    @FormUrlEncoded
    Call<Responses> addReferreds(@Field("emails") String emails,
                                 @Header("Authorization") String authorization);

    @POST("client/addAudit")
    @FormUrlEncoded
    Call<Responses> addAudit(@FieldMap HashMap<String, String> params,
                             @Header("Authorization") String authorization);

    @GET("client/audits")
    Call<Responses> getAudits(@Header("Authorization") String authorization);

    @PUT("client/cancelAudit/{audit_id}")
    Call<Responses> cancelAudit(@Path("audit_id") String auditId,
                                @Header("Authorization") String authorization);

    @POST("client/addClarification")
    @FormUrlEncoded
    Call<Responses> addClarification(@Field("type") int type,
                                     @Field("content") String content,
                                     @Header("Authorization") String authorization);

    @PUT("client/cancelClarification/{clarification_id}")
    Call<Responses> cancelClarification(@Path("clarification_id") String id,
                                        @Header("Authorization") String authorization);

    @GET("client/getClarifications")
    Call<Responses> getClarifications(@Header("Authorization") String authorization);

    @POST("client/addCard")
    Call<Responses> addCard(@Body CreditCard creditCard,
                            @Header("Authorization") String authorization);

    @FormUrlEncoded
    @HTTP(method = "DELETE", hasBody = true, path="client/dropCard")
    Call<Responses> dropCard(@Field("card_id") String cardId,
                             @Header("Authorization") String authorization);

    @POST("client/addPaymentPaypal")
    Call<Responses> addPaypalPayment(@Body Paypal paypal,
                                     @Header("Authorization") String authorization);

    @FormUrlEncoded
    @HTTP(method = "PUT", hasBody = true, path="client/mainCard")
    Call<Responses> mainCard(@Field("card_id") String cardId,
                             @Header("Authorization") String authorization);

    @GET("client/cards")
    Call<Responses> getCards2(@Header("Authorization") String authorization);

    @GET("client/cards")
    Call<Responses> getCards(@Header("Authorization") String authorization);


    @GET("client/getcards")
    Call<Responses> getCard(@Header("Authorization") String authorization);

    @POST("client/cambioTarjetas{id}")
    Call<Responses> getCardprincipal(@Header("Authorization") String authorization,
                                     @Path("id") String id,
                                     @Body Tarjetas api);

    @POST("client/addPayment")
    @FormUrlEncoded
    Call<Responses> addPayment(@Field("device_session_id") String deciveSessionId,
                               @Header("Authorization") String authorization);

    @GET("client/getAnnualCost")
    Call<Responses> getAnnualCost(@Header("Authorization") String authorization);

    @GET("client/getUrgentCost")
    Call<Responses> getUrgentCost(@Header("Authorization") String authorization);

    @GET("client/getAccountStatus")
    Call<Responses> getAccountStatus(@Header("Authorization") String authorization);

    @GET("client/types")
    Call<Responses> getTypes(@Header("Authorization") String authorization);

    @PUT("client/recoveryPassword")
    @FormUrlEncoded
    Call<Responses> recoveryPassword(@Field("email") String email,
                                     @Header("Authorization") String authorization);

    @POST("client/changePassword")
    @FormUrlEncoded
    Call<Responses> changePassword(@Field("password") String password,
                                   @Field("newpassword") String newPassword,@Header("Authorization") String authorization);

    @GET("client/address")
    Call<Responses> getAddresses(@Header("Authorization") String authorization);

    @POST("client/addAddress")
    Call<Responses> addAddress(@Header("Authorization") String authorization, @Body Address address);

    @PUT("client/updateAddress/{address_id}")
    Call<Responses> updateAddress(@Header("Authorization") String authorization, @Body Address address, @Path("address_id") String addressId);

    @DELETE("client/deleteAddress/{address_id}")
    Call<Responses> deleteAddress(@Header("Authorization") String authorization, @Path("address_id") String addressId);

    @FormUrlEncoded
    @HTTP(method = "DELETE", hasBody = true)
    Call<Responses> logout(@Url String url,
                           @FieldMap HashMap<String, String> params,
                           @Header("Authorization") String authorization);
}
