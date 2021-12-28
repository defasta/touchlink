package apps.eduraya.e_parking.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

}