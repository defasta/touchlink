package apps.eduraya.e_parking.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.LoginResponse
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.data.responses.user.UserData
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AppsRepository
    ) : BaseViewModel(repository) {

    private val _logineResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _logineResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _logineResponse.value = Resource.Loading
        _logineResponse.value = repository.login(email, password)
    }

    fun insertUserInfoDB(userInfo: UserInfo){
        repository.insertUserInfoDB(userInfo)
    }

    suspend fun saveAccessTokens(accessToken:String){
        repository.saveAccessTokens(accessToken)
    }
}