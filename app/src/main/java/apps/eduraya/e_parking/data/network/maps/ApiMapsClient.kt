package apps.eduraya.e_parking.data.network.maps

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiMapsClient {

    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/"
        fun getClient(): ApiMapsInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiMapsInterface::class.java)
        }
    }
}