package apps.eduraya.genius.ui.content

import androidx.lifecycle.*
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.GetListContentResponse
import apps.eduraya.genius.data.responses.GetListCourseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    companion object{
        const val KEY_ID_COURSE = "KEY_ID_COURSE"
    }

    private val _idCourse = savedStateHandle.getLiveData<String>(KEY_ID_COURSE)
    val idCourse :LiveData<String>
        get() = _idCourse

    private val _getListContentResponse: MutableLiveData<Resource<GetListContentResponse>> = MutableLiveData()
    val getListContentResponse: LiveData<Resource<GetListContentResponse>>
        get() =  _getListContentResponse

    fun getListContent(token: String, courseId: String) = viewModelScope.launch{
        _getListContentResponse.value = Resource.Loading
        _getListContentResponse.value = repository.getListContent(token, courseId)
    }
}