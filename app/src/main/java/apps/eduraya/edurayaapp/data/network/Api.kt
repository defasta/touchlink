package apps.eduraya.edurayaapp.data.network

import apps.eduraya.edurayaapp.data.responses.*
import apps.eduraya.edurayaapp.data.responses.user.GetDataUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {
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

    @Multipart
    @POST("auth/change-profile")
    suspend fun changeProfile(
        @Header("Authorization") authHeader: String,
        @Part("name") name:RequestBody,
        @Part("email") email: RequestBody,
        @Part avatar: MultipartBody.Part,
        @Part("phone") phone: RequestBody
    ):EditProfileResponse

    @FormUrlEncoded
    @POST("auth/request-reset-password")
    suspend fun requestResetPassword(
        @Field("email") email:String,
    ): RequestResetPasswordResponse

    @FormUrlEncoded
    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Field("email") email:String,
        @Field("token") token:String,
        @Field("password") password:String,
        @Field("password_confirmation") passwordC:String,
    ): RequestResetPasswordResponse
}