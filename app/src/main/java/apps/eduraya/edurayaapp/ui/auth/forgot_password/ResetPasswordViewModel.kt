package apps.eduraya.edurayaapp.ui.auth.forgot_password

import androidx.lifecycle.*
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.data.repository.AppsRepository
import apps.eduraya.edurayaapp.data.responses.RequestResetPasswordResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    companion object{
        const val KEY_EMAIL = "KEY_EMAIL"
    }

    private val _email = savedStateHandle.getLiveData<String>(KEY_EMAIL)
    val email : LiveData<String>
        get() = _email

    private val _resetPasswordResponse: MutableLiveData<Resource<RequestResetPasswordResponse>> = MutableLiveData()
    val resetPasswordResponse: LiveData<Resource<RequestResetPasswordResponse>>
        get() = _resetPasswordResponse

    fun resetPassword(email:String, token: String, password: String, passwordC:String) = viewModelScope.launch {
        _resetPasswordResponse.value = Resource.Loading
        _resetPasswordResponse.value = repository.resetPassword(email, token, password, passwordC)
    }
}