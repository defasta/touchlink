package apps.eduraya.e_parking.ui.vehicle_type

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.vehicle.GetAllVehicleResponse
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseVehicleViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
):BaseViewModel(repository){

    companion object {
        const val KEY_NAME_PLACE = "KEY_NAME_PLACE"
        const val KEY_ADDRESS_PLACE = "KEY_ADDRESS_PLACE"
        const val KEY_TOTAL_MOTOR = "KEY_TOTAL_MOTOR"
        const val KEY_TOTAL_CAR = "KEY_TOTAL_CAR"
    }

    private val _namePlace = savedStateHandle.getLiveData<String>(KEY_NAME_PLACE)
    val namePlace: LiveData<String>
        get() = _namePlace

    private val _addressPlace = savedStateHandle.getLiveData<String>(KEY_ADDRESS_PLACE)
    val addressPlace : LiveData<String>
        get() = _addressPlace

    private val _totalMotor = savedStateHandle.getLiveData<String>(KEY_TOTAL_MOTOR)
    val totalMotor : LiveData<String>
        get() = _totalMotor

    private val _totalCar = savedStateHandle.getLiveData<String>(KEY_TOTAL_CAR)
    val totalCar : LiveData<String>
        get() = _totalCar

    private val _getAllVehicleResponse: MutableLiveData<Resource<GetAllVehicleResponse>> = MutableLiveData()
    val getAllVehicleResponse: LiveData<Resource<GetAllVehicleResponse>>
        get() = _getAllVehicleResponse

    fun setAllVehicleResponse(
        token: String
    ) = viewModelScope.launch {
        _getAllVehicleResponse.value = Resource.Loading
        _getAllVehicleResponse.value = repository.getALlVehicle(token)
    }


}