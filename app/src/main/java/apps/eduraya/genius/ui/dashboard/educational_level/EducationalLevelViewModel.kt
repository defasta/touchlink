package apps.eduraya.genius.ui.dashboard.educational_level

import androidx.lifecycle.*
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.GetListAllContentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EducationalLevelViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    companion object{
        const val KEY_EDUCATIONAL_LEVEL = "KEY_EDUCATIONAL_LEVEL"
    }

    private val _educationalLevel = savedStateHandle.getLiveData<String>(KEY_EDUCATIONAL_LEVEL)
    val educationalLevel :LiveData<String>
        get() = _educationalLevel

    private val _getEducationalLevelContentResponse: MutableLiveData<Resource<GetListAllContentResponse>> = MutableLiveData()
    val getEducationalLevelContentResponse: LiveData<Resource<GetListAllContentResponse>>
        get() =  _getEducationalLevelContentResponse

    fun getEducationalLevelContent(token: String, title: String) = viewModelScope.launch{
        _getEducationalLevelContentResponse.value = Resource.Loading
        _getEducationalLevelContentResponse.value = repository.getEducationalLevelContent(token, title)
    }
}