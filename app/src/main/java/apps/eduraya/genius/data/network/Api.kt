package apps.eduraya.genius.data.network

import apps.eduraya.genius.data.responses.*
import apps.eduraya.genius.data.responses.user.GetDataUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("register")
    suspend fun signUp(
        @Part("name") name:RequestBody,
        @Part("email") email: RequestBody,
        @Part("birth_date") birth_date: RequestBody,
        @Part("birth_place") birth_place: RequestBody,
        @Part("address") address: RequestBody,
        @Part("educational_level") educational_level: RequestBody,
        @Part("password") password: RequestBody,
    ): SignUpResponse

    @GET("logout")
    suspend fun logout(
        @Header("Authorization") authHeader: String,
    ): LogoutResponse

    @GET("profile/{id}}")
    suspend fun getUserData(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): EditProfileResponse

    @GET("classroom")
    suspend fun getListClassroom(
        @Header("Authorization") authHeader: String,
    ): GetListClassroomResponse

    @GET("courseList/{id}")
    suspend fun getListCourse(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): GetListCourseResponse

    @GET("contentList/{id}")
    suspend fun getListContent(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): GetListContentResponse

    @GET("content")
    suspend fun searchContent(
        @Header("Authorization") authHeader: String,
        @Query("search") title: String
    ): GetListAllContentResponse

    @GET("content")
    suspend fun getEducationalLevelContent(
        @Header("Authorization") authHeader: String,
        @Query("educational_level") educationalLevel: String
    ): GetListAllContentResponse

    @GET("content")
    suspend fun getListAllContent(
        @Header("Authorization") authHeader: String,
        @Query("page") page: String
    ): GetListAllContentResponse

    @GET("content/{id}")
    suspend fun getDetailContent(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): GetDetailContentResponse

    @Multipart
    @POST("profile/{id}")
    suspend fun changeProfile(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
        @Part("name") name:RequestBody,
        @Part("birth_date") birth_date: RequestBody,
        @Part("birth_place") birth_place: RequestBody,
        @Part("address") address: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("educational_level") educational_level: RequestBody,
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