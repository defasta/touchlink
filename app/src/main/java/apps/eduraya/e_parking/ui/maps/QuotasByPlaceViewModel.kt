package apps.eduraya.e_parking.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.auth.AppsRepository
import apps.eduraya.e_parking.data.repository.auth.BaseRepository
import apps.eduraya.e_parking.data.responses.getplace.GetQuotasByPlaceResponse
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotasByPlaceViewModel @Inject constructor(
    private val repository: AppsRepository
): BaseViewModel(repository){

    private val _getQuotasByPlace: MutableLiveData<Resource<GetQuotasByPlaceResponse>> = MutableLiveData()
    val getQuotasByPlaceResult: LiveData<Resource<GetQuotasByPlaceResponse>>
     get() = _getQuotasByPlace

    fun setQuotasByResult(token: String) = viewModelScope.launch {
        _getQuotasByPlace.value = Resource.Loading
        _getQuotasByPlace.value = repository.getQuotasByPlace(token)
    }
}