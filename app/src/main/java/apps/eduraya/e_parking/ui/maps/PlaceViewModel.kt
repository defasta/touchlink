package apps.eduraya.e_parking.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.GetPlacesResponse
import apps.eduraya.e_parking.data.responses.ListDataPlace
import apps.eduraya.e_parking.data.responses.getplace.ListDataQuotasByPlace
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: AppsRepository
) : BaseViewModel(repository){

    private val _getPlacesResponse: MutableLiveData<Resource<GetPlacesResponse>> = MutableLiveData()
    val getPlacesResult: LiveData<Resource<GetPlacesResponse>>
        get() = _getPlacesResponse

    fun setPlaceResult(token:String) = viewModelScope.launch {
        _getPlacesResponse.value = Resource.Loading
        _getPlacesResponse.value = repository.getPlaces(token)
    }

}