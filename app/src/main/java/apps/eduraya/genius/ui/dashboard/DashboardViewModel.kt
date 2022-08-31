package apps.eduraya.genius.ui.dashboard

import androidx.lifecycle.*
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.*
import apps.eduraya.genius.data.responses.user.GetDataUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){


    private val _isAllContent  : MutableLiveData<Boolean> = MutableLiveData()
    val isAllContent : LiveData<Boolean>
        get() = _isAllContent

    fun setIsAllContent(isAllContent:Boolean){
        _isAllContent.postValue(isAllContent)
    }

    private val _searchContentResponse: MutableLiveData<Resource<GetListAllContentResponse>> = MutableLiveData()
    val searchContentResponse: LiveData<Resource<GetListAllContentResponse>>
        get() =  _searchContentResponse

    fun searchContent(token: String, title: String) = viewModelScope.launch{
        _searchContentResponse.value = Resource.Loading
        _searchContentResponse.value = repository.searchContent(token, title)
    }

    private val _getListAllContentResponse: MutableLiveData<Resource<GetListAllContentResponse>> = MutableLiveData()
    val getListAllContentResponse: LiveData<Resource<GetListAllContentResponse>>
        get() =  _getListAllContentResponse

    fun getListAllContent(token: String, page: String) = viewModelScope.launch{
        _getListAllContentResponse.value = Resource.Loading
        _getListAllContentResponse.value = repository.getListAllContent(token, page)
    }

    private val _getListClassroomResponse: MutableLiveData<Resource<GetListClassroomResponse>> = MutableLiveData()
    val getListClassroomResponse: LiveData<Resource<GetListClassroomResponse>>
        get() =  _getListClassroomResponse

    fun getListClassroom(token: String) = viewModelScope.launch{
        _getListClassroomResponse.value = Resource.Loading
        _getListClassroomResponse.value = repository.getListClassroom(token)
    }

    private val _getEducationalLevelContentResponse: MutableLiveData<Resource<GetListAllContentResponse>> = MutableLiveData()
    val getEducationalLevelContentResponse: LiveData<Resource<GetListAllContentResponse>>
        get() =  _getEducationalLevelContentResponse

    fun getEducationalLevelContent(token: String, title: String) = viewModelScope.launch{
        _getEducationalLevelContentResponse.value = Resource.Loading
        _getEducationalLevelContentResponse.value = repository.getEducationalLevelContent(token, title)
    }

}