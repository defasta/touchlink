package apps.eduraya.edurayaapp.ui.auth.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.data.repository.AppsRepository
import apps.eduraya.edurayaapp.data.responses.RequestResetPasswordResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestResetPasswordViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel(){
    private val _requestResetPasswordResponse: MutableLiveData<Resource<RequestResetPasswordResponse>> = MutableLiveData()
    val requestResetPasswordResponse: LiveData<Resource<RequestResetPasswordResponse>>
        get() = _requestResetPasswordResponse

    fun requestResetPassword(email:String) = viewModelScope.launch {
        _requestResetPasswordResponse.value = Resource.Loading
        _requestResetPasswordResponse.value = repository.requestResetPassword(email)
    }
}