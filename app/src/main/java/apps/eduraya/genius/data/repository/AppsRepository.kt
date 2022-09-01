package apps.eduraya.genius.data.repository

import apps.eduraya.genius.data.db.UserPreferences
import apps.eduraya.genius.data.network.Api
import apps.eduraya.genius.data.network.SafeApiCall
import apps.eduraya.genius.data.responses.UserInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AppsRepository @Inject constructor(
    private val api: Api,
    private val preferences: UserPreferences
    ): SafeApiCall {

    suspend fun login(email: String, password:String) = safeApiCall {
        api.login(email, password)
    }

    suspend fun signUp(name: RequestBody, email: RequestBody, birthDay:RequestBody, birthPlace:RequestBody,address:RequestBody, educationalLevel: RequestBody, password:RequestBody ) = safeApiCall {
        api.signUp(name, email, birthDay, birthPlace, address, educationalLevel, password)
    }

    suspend fun getUserData(token: String, id: String) = safeApiCall{
        api.getUserData(token, id)
    }

    suspend fun getListClassroom(token: String) = safeApiCall{
        api.getListClassroom(token)
    }

    suspend fun getListCourse(token: String, classroomId: String) = safeApiCall{
        api.getListCourse(token, classroomId)
    }

    suspend fun searchContent(token: String, title: String) = safeApiCall{
        api.searchContent(token, title)
    }

    suspend fun getEducationalLevelContent(token: String, educationalLevel: String) = safeApiCall{
        api.getEducationalLevelContent(token, educationalLevel)
    }

    suspend fun getListContent(token: String, courseId: String) = safeApiCall{
        api.getListContent(token, courseId)
    }

    suspend fun getListAllContent(token: String, page: String) = safeApiCall{
        api.getListAllContent(token, page)
    }

    suspend fun getDetailContent(token: String, contentId: String) = safeApiCall{
        api.getDetailContent(token, contentId)
    }

    suspend fun changeProfile(tokenAccess: String, id: String, name:RequestBody, birthDay:RequestBody, birthPlace: RequestBody, address:RequestBody, educationalLevel: RequestBody) = safeApiCall {
        api.changeProfile(tokenAccess, id, name, birthDay, birthPlace, address, educationalLevel)
    }

    suspend fun saveAccessTokens(accessToken: String){
        preferences.saveAccessTokens(accessToken)
    }

    suspend fun saveUserId(userId: String){
        preferences.saveUserId(userId)
    }

    suspend fun logoutAccount(){
        preferences.clear()
    }

    suspend fun resetPassword(token: String, id: String, oldPassword: String, newPassword: String) = safeApiCall {
        api.resetPassword(token, id, oldPassword, newPassword)
    }
}