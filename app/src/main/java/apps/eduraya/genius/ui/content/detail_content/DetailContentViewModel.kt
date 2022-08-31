package apps.eduraya.genius.ui.content.detail_content

import androidx.lifecycle.*
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.GetDetailContentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailContentViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    companion object{
        const val KEY_ID_CONTENT = "KEY_ID_CONTENT"
    }
    private val _idContent = savedStateHandle.getLiveData<String>(KEY_ID_CONTENT)
    val idContent : LiveData<String>
        get() = _idContent

    private val _getDetailContentResponse: MutableLiveData<Resource<GetDetailContentResponse>> = MutableLiveData()
    val getDetailContentResponse: LiveData<Resource<GetDetailContentResponse>>
        get() =  _getDetailContentResponse

    fun getDetailContent(token: String, contentId: String) = viewModelScope.launch{
        _getDetailContentResponse.value = Resource.Loading
        _getDetailContentResponse.value = repository.getDetailContent(token, contentId)
    }

}