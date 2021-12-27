package apps.eduraya.e_parking.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.auth.AppsRepository
import apps.eduraya.e_parking.data.responses.SignUpResponse
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AppsRepository
): BaseViewModel(repository) {

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