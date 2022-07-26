package apps.eduraya.edurayaapp.ui.profile

import androidx.lifecycle.*
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.data.repository.AppsRepository
import apps.eduraya.edurayaapp.data.responses.EditProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object{
        const val KEY_NAME_USER = "KEY_NAME_USER"
        const val KEY_EMAIL_USER = "KEY_EMAIL_USER"
        const val KEY_PHONE_USER = "KEY_PHONE_USER"
        const val KEY_AVATAR_USER = "KEY_AVATAR_USER"
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

    private val _userAvatar = savedStateHandle.getLiveData<String>(KEY_AVATAR_USER)
    val userAvatar: LiveData<String>
        get() = _userAvatar

    private val _editProfileResponse: MutableLiveData<Resource<EditProfileResponse>> = MutableLiveData()
    val editProfileResponse : LiveData<Resource<EditProfileResponse>>
        get() = _editProfileResponse

    fun editProfile(token: String, name: RequestBody, email: RequestBody, avatar: MultipartBody.Part, phone: RequestBody) = viewModelScope.launch {
        _editProfileResponse.value = Resource.Loading
        _editProfileResponse.value = repository.changeProfile(token, name, email, avatar, phone)
    }
}