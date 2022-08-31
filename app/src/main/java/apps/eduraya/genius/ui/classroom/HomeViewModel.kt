package apps.eduraya.genius.ui.classroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.EditProfileResponse
import apps.eduraya.genius.data.responses.GetListClassroomResponse
import apps.eduraya.genius.data.responses.LogoutResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppsRepository
):ViewModel(){

    private val _userInfoResponse: MutableLiveData<Resource<EditProfileResponse>> = MutableLiveData()
    val userInfoResponse: LiveData<Resource<EditProfileResponse>>
        get() =  _userInfoResponse


    private val _getListClassroomResponse: MutableLiveData<Resource<GetListClassroomResponse>> = MutableLiveData()
    val getListClassroomResponse: LiveData<Resource<GetListClassroomResponse>>
        get() =  _getListClassroomResponse


    private val _logoutResponse: MutableLiveData<Resource<LogoutResponse>> = MutableLiveData()
    val logoutResponse: LiveData<Resource<LogoutResponse>>
        get() =  _logoutResponse

    fun getUserInfo(token: String, id: String) = viewModelScope.launch{
        _userInfoResponse.value = Resource.Loading
        _userInfoResponse.value = repository.getUserData(token, id)
    }

    fun getListClassroom(token: String) = viewModelScope.launch{
        _getListClassroomResponse.value = Resource.Loading
        _getListClassroomResponse.value = repository.getListClassroom(token)
    }

//    fun logoutAccount(token: String) = viewModelScope.launch{
//        repository.logoutAccount(token)
//    }

    suspend fun logoutAccount(){
        repository.logoutAccount()
    }
}