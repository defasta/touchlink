package apps.eduraya.e_parking.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.EditProfileResponse
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel(repository) {

    companion object{
        const val KEY_NAME_USER = "KEY_NAME_USER"
        const val KEY_EMAIL_USER = "KEY_EMAIL_USER"
        const val KEY_PHONE_USER = "KEY_PHONE_USER"
    }

    private val _userName = savedStateHandle.getLiveData<String>(KEY_NAME_USER)
    val userName: LiveData<String>
        get() = _userName

    private val _userPhone = savedStateHandle.getLiveData<String>(KEY_PHONE_USER)
    val userPhone: LiveData<String>
        get() = _userPhone

    private val _userEmail = savedStateHandle.getLiveData<String>(KEY_EMAIL_USER)
    val userEmail: LiveData<String>
        get() = _userEmail

    private val _getEditProfileResponse: MutableLiveData<Resource<EditProfileResponse>> = MutableLiveData()
    val getEditProfileResponse : LiveData<Resource<EditProfileResponse>>
        get() = _getEditProfileResponse

    fun setEditProfileResponse(token: String, name: String, email: String, avatar: String, phone: String) = viewModelScope.launch {
        _getEditProfileResponse.value = Resource.Loading
        _getEditProfileResponse.value = repository.changeProfile(token, name, email, avatar, phone)
    }
}