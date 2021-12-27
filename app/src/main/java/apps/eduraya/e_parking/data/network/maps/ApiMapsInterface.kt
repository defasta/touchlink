package apps.eduraya.e_parking.data.network.maps

import apps.eduraya.e_parking.data.responses.ModelResultDetail
import apps.eduraya.e_parking.data.responses.ModelResultNearby
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMapsInterface {
    @GET("/place/nearbysearch/json")
    fun getDataResult(@Query("key") key: String,
                      @Query("keyword") keyword: String,
                      @Query("location") location: String,
                      @Query("rankby") rankby: String): Call<ModelResultNearby>

    @GET("/place/details/json")
    fun getDetailResult(@Query("key") key: String,
                        @Query("placeid") placeid: String): Call<ModelResultDetail>
}