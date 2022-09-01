package apps.eduraya.genius.ui.auth.forgot_password

import androidx.lifecycle.*
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.RequestResetPasswordResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: AppsRepository,
):ViewModel(){

    private val _resetPasswordResponse: MutableLiveData<Resource<RequestResetPasswordResponse>> = MutableLiveData()
    val resetPasswordResponse: LiveData<Resource<RequestResetPasswordResponse>>
        get() = _resetPasswordResponse

    fun resetPassword(token: String, id:String, oldPassword: String, newPassword:String) = viewModelScope.launch {
        _resetPasswordResponse.value = Resource.Loading
        _resetPasswordResponse.value = repository.resetPassword(token, id, oldPassword, newPassword)
    }
}