package apps.eduraya.e_parking.data.repository

import apps.eduraya.e_parking.data.db.AppDao
import apps.eduraya.e_parking.data.db.UserPreferences
import apps.eduraya.e_parking.data.network.Api
import apps.eduraya.e_parking.data.responses.DataDeposit
import apps.eduraya.e_parking.data.responses.DataPayDeposit
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.data.responses.user.UserData
import javax.inject.Inject

class AppsRepository @Inject constructor(
    private val api: Api,
    private val preferences: UserPreferences,
    private val appDao: AppDao
    ): BaseRepository(api){

    suspend fun login(email: String, password:String) = safeApiCall {
        api.login(email, password)
    }

    suspend fun signUp(name: String, email: String, password:String, passwordC:String) = safeApiCall {
        api.signUp(name, email, password, passwordC)
    }

    suspend fun getUserData(token: String) = safeApiCall{
        api.getUserData(token)
    }

    suspend fun saveAccessTokens(accessToken: String){
        preferences.saveAccessTokens(accessToken)
    }

    suspend fun getPlaces(token: String) = safeApiCall {
        api.getPlaces(token)
    }

    suspend fun getQuotasByPlace(token: String, id:String) = safeApiCall {
        api.getQuotasByPlace(token,id)
    }

    fun getUserInfoDB():List<UserInfo>{
        return appDao.getUserInfo()
    }

    fun insertUserInfoDB(userInfo: UserInfo){
        appDao.insertUserInfo(userInfo)
    }

    suspend fun createDeposit(token: String, nominal: String) = safeApiCall {
        api.createDeposit(token, nominal)
    }

    suspend fun getTokenDeposit(tokenAccess: String, id: String) = safeApiCall{
        api.getTokenDeposit(tokenAccess, id)
    }

    suspend fun saveDepositToken(token: String) {
        preferences.saveAccessTokens(token)
    }

    suspend fun getAllDeposit(tokenAccess: String) = safeApiCall{
        api.getAllDeposit(tokenAccess)
    }

    suspend fun getALlVehicle(tokenAccess:String) = safeApiCall {
        api.getAllDVehicle(tokenAccess)
    }

}