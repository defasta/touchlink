package apps.eduraya.e_parking.data.repository.auth

import apps.eduraya.e_parking.data.network.auth.BaseApi
import apps.eduraya.e_parking.data.network.SafeApiCall

abstract class BaseRepository (private val api: BaseApi): SafeApiCall{
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}