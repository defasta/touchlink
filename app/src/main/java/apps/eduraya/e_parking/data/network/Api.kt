package apps.eduraya.e_parking.data.network

import apps.eduraya.e_parking.data.responses.*
import apps.eduraya.e_parking.data.responses.deposit.GetDepositResponse
import apps.eduraya.e_parking.data.responses.getplace.GetQuotasByPlaceResponse
import apps.eduraya.e_parking.data.responses.insurance.GetInsuranceResponse
import apps.eduraya.e_parking.data.responses.lastparking.GetLastParkingResponse
import apps.eduraya.e_parking.data.responses.reservation.ReservationResponse
import apps.eduraya.e_parking.data.responses.reservations.GetAllReservationsResponse
import apps.eduraya.e_parking.data.responses.user.GetDataUserResponse
import apps.eduraya.e_parking.data.responses.valet.GetValetAreaResponse
import apps.eduraya.e_parking.data.responses.vehicle.GetAllVehicleResponse
import okhttp3.MultipartBody
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

    @Multipart
    @POST("auth/change-profile")
    suspend fun changeProfile(
        @Header("Authorization") authHeader: String,
        @Field("name") name:String,
        @Field("email") email: String,
        @Part("avatar") avatar: String,
        @Field("phone") phone: String
    ):EditProfileResponse

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

    @GET("utils/vehicles")
    suspend fun getAllDVehicle(
        @Header("Authorization") authHeader: String
    ):GetAllVehicleResponse

    @GET("places/{id}/valet-areas")
    suspend fun getValetAreasByPlace(
        @Header("Authorization") authHeader: String,
        @Path("id") id:String
    ): GetValetAreaResponse

    @FormUrlEncoded
    @POST("parkings/reservation")
    suspend fun createReservation(
        @Header("Authorization") authHeader: String,
        @Field("valet_area_id") valetAreaId: String,
        @Field("check_in") checkIn: String
    ): ReservationResponse

    @GET("parkings")
    suspend fun getAllReservation(
        @Header("Authorization") authHeader: String,
    ): GetAllReservationsResponse

    @GET("utils/insurance")
    suspend fun getInsurance(
        @Header("Authorization") authHeader: String,
    ): GetInsuranceResponse

    @GET("parkings/last")
    suspend fun getLastParking(
        @Header("Authorization") authHeader: String,
    ): GetLastParkingResponse
}