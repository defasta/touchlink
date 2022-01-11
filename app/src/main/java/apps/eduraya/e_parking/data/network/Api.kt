package apps.eduraya.e_parking.data.network

import apps.eduraya.e_parking.data.responses.*
import apps.eduraya.e_parking.data.responses.deposit.GetDepositResponse
import apps.eduraya.e_parking.data.responses.getplace.GetQuotasByPlaceResponse
import apps.eduraya.e_parking.data.responses.user.GetDataUserResponse
import retrofit2.http.*

interface Api: BaseApi {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun signUp(
        @Field("name") name:String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String
    ): SignUpResponse

    @GET("auth/me")
    suspend fun getUserData(
        @Header("Authorization") authHeader: String,
    ): GetDataUserResponse

    @GET("places")
    suspend fun getPlaces(
        @Header("Authorization") authHeader: String,
    ): GetPlacesResponse

    @GET("places/{id}/quotas")
    suspend fun getQuotasByPlace(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
    ): GetQuotasByPlaceResponse

    @FormUrlEncoded
    @POST("deposits")
    suspend fun createDeposit(
        @Header("Authorization") authHeader: String,
        @Field("amount") nominal: String
    ):CreateDepositResponse

    @POST("deposits/{id}/pay")
    suspend fun getTokenDeposit(
        @Header("Authorization") authHeader: String,
        @Path("id") id:String
    ): PayDepositResponse

    @GET("deposits")
    suspend fun getAllDeposit(
        @Header("Authorization") authHeader: String
    ):GetDepositResponse
}