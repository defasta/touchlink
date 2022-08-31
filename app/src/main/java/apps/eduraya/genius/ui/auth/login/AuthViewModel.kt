package apps.eduraya.genius.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.LoginResponse
import apps.eduraya.genius.data.responses.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AppsRepository
    ) : ViewModel() {

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

    suspend fun saveAccessTokens(accessToken:String){
        repository.saveAccessTokens(accessToken)
    }

    suspend fun saveUserId(userId: String){
        repository.saveUserId(userId)
    }
}