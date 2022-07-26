package apps.eduraya.edurayaapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.data.repository.AppsRepository
import apps.eduraya.edurayaapp.data.responses.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel(){

    private val _registerResponse: MutableLiveData<Resource<SignUpResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<SignUpResponse>>
        get() = _registerResponse

    fun signup(
        name: String,
        email: String,
        password: String,
        passwordC: String
    ) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.signUp(name, email, password, passwordC)
    }

    suspend fun saveAccessTokens(accessToken:String){
        repository.saveAccessTokens(accessToken)
    }
}