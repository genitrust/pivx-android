package pivx.org.pivxwallet.wallofcoins.api;

import java.util.List;
import java.util.Map;

import pivx.org.pivxwallet.wallofcoins.request.CreateAuthReq;
import pivx.org.pivxwallet.wallofcoins.response.AdsListActivityResp;
import pivx.org.pivxwallet.wallofcoins.response.CaptureHoldResp;
import pivx.org.pivxwallet.wallofcoins.response.CheckAuthResp;
import pivx.org.pivxwallet.wallofcoins.response.ConfirmDepositResp;
import pivx.org.pivxwallet.wallofcoins.response.CreateAdResp;
import pivx.org.pivxwallet.wallofcoins.response.CreateAuthResp;
import pivx.org.pivxwallet.wallofcoins.response.CreateDeviceResp;
import pivx.org.pivxwallet.wallofcoins.response.CreateHoldResp;
import pivx.org.pivxwallet.wallofcoins.response.CurrentAuthResp;
import pivx.org.pivxwallet.wallofcoins.response.DiscoveryInputsResp;
import pivx.org.pivxwallet.wallofcoins.response.GetAuthTokenResp;
import pivx.org.pivxwallet.wallofcoins.response.GetCurrencyResp;
import pivx.org.pivxwallet.wallofcoins.response.GetHoldsResp;
import pivx.org.pivxwallet.wallofcoins.response.GetOffersResp;
import pivx.org.pivxwallet.wallofcoins.response.GetPricingOptionsResp;
import pivx.org.pivxwallet.wallofcoins.response.GetReceivingOptionsResp;
import pivx.org.pivxwallet.wallofcoins.response.OrderListResp;
import pivx.org.pivxwallet.wallofcoins.response.SendVerificationResp;
import pivx.org.pivxwallet.wallofcoins.response.VerifyAdResp;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * RestApi Client Interface For all WOC RestFull API with Method
 * Get,Post,Update & Delete API call
 */
public interface RestApi {

    @GET("api/v1/auth/current/")
    Call<CurrentAuthResp> getCurrentAuth();

    @POST("api/v1/auth/")
    Call<CreateAuthResp> createAuth(@Body CreateAuthReq createAuthReq);

    @GET("api/v1/orders/")
    Call<List<OrderListResp>> getOrders(@Query("publisherId") String publisherId);


    @GET("api/v1/auth/{phone}/")
    Call<CheckAuthResp> checkAuth(@Path("phone") String username, @Query("publisherId") String publisherId);

    @DELETE("api/v1/auth/{phone}/")
    Call<CheckAuthResp> deleteAuth(@Path("phone") String username, @Query("publisherId") String publisherId);

    @DELETE("api/v1/orders/{orderId}/")
    Call<Void> cancelOrder(@Path("orderId") String orderId, @Query("publisherId") String publisherId);

    @FormUrlEncoded
    @POST("api/v1/auth/{phone}/authorize/")
    Call<GetAuthTokenResp> getAuthToken(@Path("phone") String username, @FieldMap Map<String, String> partMap);

    @GET("api/v1/banks/")
    Call<List<GetReceivingOptionsResp>> getReceivingOptions(@Query("country") String country, @Query("publisherId") String publisherId);


    //--------------dash wizard
    @GET("api/v1/banks/")
    Call<List<GetReceivingOptionsResp>> getReceivingOptions();
    //----------------------

    @GET("api/v1/ad/")
    Call<List<AdsListActivityResp>> getAdsListing();

    @GET("api/v1/markets/{crypto}/{currency}/")
    Call<List<GetPricingOptionsResp>> getPricingOptions(@Path("crypto") String crypto, @Path("currency") String currency);

    @GET("api/v1/currency/")
    Call<List<GetCurrencyResp>> getCurrency();

    @FormUrlEncoded
    @POST("api/adcreate/")
    Call<CreateAdResp> createAd(@FieldMap Map<String, Object> partMap);

    @FormUrlEncoded
    @POST("api/sendVerification/")
    Call<SendVerificationResp> sendVerification(@FieldMap Map<String, Object> partMap);

    @FormUrlEncoded
    @POST("api/verifyAd/")
    Call<VerifyAdResp> verifyAd(@FieldMap Map<String, String> partMap);

    @FormUrlEncoded
    @POST("api/v1/discoveryInputs/")
    Call<DiscoveryInputsResp> discoveryInputs(@FieldMap Map<String, String> partMap);

    @GET("api/v1/discoveryInputs/{discoveryId}/offers/")
    Call<GetOffersResp> getOffers(@Path("discoveryId") String discoveryId, @Query("publisherId") String publisherId);

    @FormUrlEncoded
    @POST("api/v1/holds/")
    Call<CreateHoldResp> createHold(@FieldMap Map<String, String> partMap);

    @GET("api/v1/holds/")
    Call<List<GetHoldsResp>> getHolds();

    @DELETE("api/v1/holds/{id}/")
    Call<Void> deleteHold(@Path("id") String id);

    @FormUrlEncoded
    @POST("api/v1/holds/{id}/capture/")
    Call<List<CaptureHoldResp>> captureHold(@Path("id") String id, @FieldMap Map<String, String> partMap);

    @FormUrlEncoded
    @POST("api/v1/orders/{holdId}/confirmDeposit/")
    Call<ConfirmDepositResp> confirmDeposit(@Path("holdId") String holdId, @Field("your_field") String yourField, @Query("publisherId") String publisherId);

    @FormUrlEncoded
    @POST("api/v1/devices/")
    Call<CreateDeviceResp> createDevice(@FieldMap Map<String, String> partMap);

    @GET("api/v1/devices/")
    Call<List<CreateDeviceResp>> getDevice();

}
