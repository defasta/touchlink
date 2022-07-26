package apps.eduraya.edurayaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.eduraya.edurayaapp.data.network.Resource
import apps.eduraya.edurayaapp.data.repository.AppsRepository
import apps.eduraya.edurayaapp.data.responses.UserInfo
import apps.eduraya.edurayaapp.data.responses.user.GetDataUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppsRepository
):ViewModel(){

    var userInfoDB: MutableLiveData<List<UserInfo>> = MutableLiveData()

    init {
        loadUserInfoDB()
    }

    fun loadUserInfoDB(){
        val list = repository.getUserInfoDB()
        userInfoDB.postValue(list)
    }

    private val _userInfoResponse: MutableLiveData<Resource<GetDataUserResponse>> = MutableLiveData()
    val userInfoResponse: LiveData<Resource<GetDataUserResponse>>
        get() =  _userInfoResponse

    fun getUserInfo(token: String) = viewModelScope.launch{
        _userInfoResponse.value = Resource.Loading
        _userInfoResponse.value = repository.getUserData(token)
    }

    suspend fun logoutAccount(){
        repository.logoutAccount()
    }
}