package apps.eduraya.genius.ui.course

import androidx.lifecycle.*
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.GetListCourseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object{
        const val KEY_ID_CLASSROOM = "KEY_ID_CLASSROOM"
    }

    private val _idClassroom = savedStateHandle.getLiveData<String>(KEY_ID_CLASSROOM)
    val idClassroom: LiveData<String>
        get() = _idClassroom

    private val _getListCourseResponse: MutableLiveData<Resource<GetListCourseResponse>> = MutableLiveData()
    val getListCourseResponse: LiveData<Resource<GetListCourseResponse>>
        get() =  _getListCourseResponse

    fun getListCourse(token: String, classroomId: String) = viewModelScope.launch{
        _getListCourseResponse.value = Resource.Loading
        _getListCourseResponse.value = repository.getListCourse(token, classroomId)
    }
}