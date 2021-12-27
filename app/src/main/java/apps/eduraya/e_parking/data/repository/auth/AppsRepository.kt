package apps.eduraya.e_parking.data.repository.auth

import apps.eduraya.e_parking.data.UserPreferences
import apps.eduraya.e_parking.data.network.auth.Api
import javax.inject.Inject

class AppsRepository @Inject constructor(
    private val api: Api,
    private val preferences: UserPreferences
    ): BaseRepository(api){

    suspend fun login(email: String, password:String) = safeApiCall {
        api.login(email, password)
    }

    suspend fun signUp(name: String, email: String, password:String, passwordC:String) = safeApiCall {
        api.signUp(name, email, password, passwordC)
    }

    suspend fun saveAccessTokens(accessToken: String){
        preferences.saveAccessTokens(accessToken)
    }

    suspend fun getPlaces(token: String) = safeApiCall {
        api.getPlaces(token)
    }

    suspend fun getQuotasByPlace(token: String) = safeApiCall {
        api.getQuotasByPlace(token)
    }

}