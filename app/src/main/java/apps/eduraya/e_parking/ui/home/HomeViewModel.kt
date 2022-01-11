package apps.eduraya.e_parking.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.data.responses.user.GetDataUserResponse
import apps.eduraya.e_parking.data.responses.user.UserData
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppsRepository
):BaseViewModel(repository){

    var userInfoDB: MutableLiveData<List<UserInfo>> = MutableLiveData()

    init {
        loadUserInfoDB()
    }

    fun loadUserInfoDB(){
        val list = repository.getUserInfoDB()
        userInfoDB.postValue(list)
    }

    fun getUserInfoDBObserver(): MutableLiveData<List<UserInfo>>{
        return userInfoDB
    }

    private val _getUserInfoResponse: MutableLiveData<Resource<GetDataUserResponse>> = MutableLiveData()
    val getUserInfoResponse: LiveData<Resource<GetDataUserResponse>>
        get() =  _getUserInfoResponse

    fun setUserInfoResponse(token: String) = viewModelScope.launch{
        _getUserInfoResponse.value = Resource.Loading
        _getUserInfoResponse.value = repository.getUserData(token)
    }
}