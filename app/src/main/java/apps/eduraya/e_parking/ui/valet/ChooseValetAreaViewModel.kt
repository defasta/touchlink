package apps.eduraya.e_parking.ui.valet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.valet.GetValetAreaResponse
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseValetAreaViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel(repository){

    companion object{
        const val KEY_ID_PLACE = "KEY_ID_PLACE"
        const val KEY_ID_VEHICLE = "KEY_ID_VEHICLE"
    }

    private val _timePicked : MutableLiveData<String> = MutableLiveData()
    val timePicked: LiveData<String>
        get() = _timePicked

    fun setTimePicked(time:String){
        _timePicked.postValue(time)
    }

    private val _datePicked : MutableLiveData<String> = MutableLiveData()
    val datePicked: LiveData<String>
        get() = _datePicked

    fun setDatePicked(date:String){
        _datePicked.postValue(date)
    }

    private val _idPlace = savedStateHandle.getLiveData<String>(KEY_ID_PLACE)
    val idPlace: LiveData<String>
        get() = _idPlace

    private val _idVehicle = savedStateHandle.getLiveData<String>(KEY_ID_VEHICLE)
    val idVehicle: LiveData<String>
        get() = _idVehicle

    private val _getAllValetAreaResponse: MutableLiveData<Resource<GetValetAreaResponse>> = MutableLiveData()
    val getAllValetAreaResponse : LiveData<Resource<GetValetAreaResponse>>
        get() = _getAllValetAreaResponse

    fun setAllValetAreaResponse(
        token: String,
        id: String
    ) = viewModelScope.launch {
        _getAllValetAreaResponse.value = Resource.Loading
        _getAllValetAreaResponse.value = repository.getValetAreasByPlace(token, id)
    }
}