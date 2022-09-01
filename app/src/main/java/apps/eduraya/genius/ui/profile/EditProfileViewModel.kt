package apps.eduraya.genius.ui.profile

import androidx.lifecycle.*
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.EditProfileResponse
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
        const val KEY_ADDRESS_USER = "KEY_ADDRESS_USER"
        const val KEY_BIRTH_DATE_USER = "KEY_BIRTH_DATE_USER"
        const val KEY_BIRTH_PLACE_USER = "KEY_BIRTH_PLACE_USER"
        const val KEY_EDUCATION_USER = "KEY_EDUCATION_USER"
        const val KEY_PHOTO_URL_USER = "KEY_EDUCATION_USER"
    }

    private val _userName = savedStateHandle.getLiveData<String>(KEY_NAME_USER)
    val userName: LiveData<String>
        get() = _userName

    private val _userEmail = savedStateHandle.getLiveData<String>(KEY_EMAIL_USER)
    val userEmail: LiveData<String>
        get() = _userEmail

    private val _userAddress = savedStateHandle.getLiveData<String>(KEY_ADDRESS_USER)
    val userAddress: LiveData<String>
        get() = _userAddress

    private val _userBirthDate = savedStateHandle.getLiveData<String>(KEY_BIRTH_DATE_USER)
    val userBirthDate: LiveData<String>
        get() = _userBirthDate

    private val _userBirthPlace = savedStateHandle.getLiveData<String>(KEY_BIRTH_PLACE_USER)
    val userBirthPlace: LiveData<String>
        get() = _userBirthPlace

    private val _userEducationalLevel = savedStateHandle.getLiveData<String>(KEY_EDUCATION_USER)
    val userEducationalLevel: LiveData<String>
        get() = _userEducationalLevel

    private val _userPhotoUrl = savedStateHandle.getLiveData<String>(KEY_PHOTO_URL_USER)
    val userPhotoUrl: LiveData<String>
        get() = _userPhotoUrl

    private val _editProfileResponse: MutableLiveData<Resource<EditProfileResponse>> = MutableLiveData()
    val editProfileResponse : LiveData<Resource<EditProfileResponse>>
        get() = _editProfileResponse

    private val _datePicked : MutableLiveData<String> = MutableLiveData()
    val datePicked: LiveData<String>
        get() = _datePicked

    fun setDatePicked(date:String){
        _datePicked.postValue(date)
    }

    fun editProfile(tokenAccess: String, id: String,  name:RequestBody, birthDay:RequestBody, birthPlace: RequestBody, address:RequestBody, educationalLevel: RequestBody) = viewModelScope.launch {
        _editProfileResponse.value = Resource.Loading
        _editProfileResponse.value = repository.changeProfile(tokenAccess, id, name, birthDay, birthPlace, address, educationalLevel)
    }
}