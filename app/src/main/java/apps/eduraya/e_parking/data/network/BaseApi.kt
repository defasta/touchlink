package apps.eduraya.e_parking.data.network

import okhttp3.ResponseBody
import retrofit2.http.POST

interface BaseApi {
    @POST("auth/me")
    suspend fun logout():ResponseBody
}