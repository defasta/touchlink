package apps.eduraya.edurayaapp.data.repository

import apps.eduraya.edurayaapp.data.db.AppDao
import apps.eduraya.edurayaapp.data.db.UserPreferences
import apps.eduraya.edurayaapp.data.network.Api
import apps.eduraya.edurayaapp.data.network.SafeApiCall
import apps.eduraya.edurayaapp.data.responses.UserInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AppsRepository @Inject constructor(
    private val api: Api,
    private val preferences: UserPreferences,
    private val appDao: AppDao
    ): SafeApiCall {

    suspend fun login(email: String, password:String) = safeApiCall {
        api.login(email, password)
    }

    suspend fun signUp(name: String, email: String, password:String, passwordC:String) = safeApiCall {
        api.signUp(name, email, password, passwordC)
    }

    suspend fun getUserData(token: String) = safeApiCall{
        api.getUserData(token)
    }

    suspend fun changeProfile(tokenAccess: String, name:RequestBody, email:RequestBody, avatar:MultipartBody.Part, phone: RequestBody) = safeApiCall {
        api.changeProfile(tokenAccess, name, email, avatar, phone)
    }

    suspend fun saveAccessTokens(accessToken: String){
        preferences.saveAccessTokens(accessToken)
    }

    suspend fun logoutAccount(){
        preferences.clear()
    }

    fun getUserInfoDB():List<UserInfo>{
        return appDao.getUserInfo()
    }

    fun insertUserInfoDB(userInfo: UserInfo){
        appDao.insertUserInfo(userInfo)
    }

    suspend fun requestResetPassword(email:String) = safeApiCall {
        api.requestResetPassword(email)
    }

    suspend fun resetPassword(email: String, token: String, password: String, passwordC: String) = safeApiCall {
        api.resetPassword(email, token, password, passwordC)
    }
}