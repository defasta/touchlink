package apps.eduraya.e_parking.data.network

import apps.eduraya.e_parking.data.responses.GetPlacesResponse
import apps.eduraya.e_parking.data.responses.LoginResponse
import apps.eduraya.e_parking.data.responses.SignUpResponse
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

    @GET("places/1/quotas")
    suspend fun getQuotasByPlace(
        @Header("Authorization") authHeader: String,
    ): GetQuotasByPlaceResponse
}