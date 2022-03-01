package apps.eduraya.e_parking.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.reservations.GetAllReservationsResponse
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val repository: AppsRepository
):BaseViewModel(repository){

    private val _getAllReservationResponse: MutableLiveData<Resource<GetAllReservationsResponse>> = MutableLiveData()
    val getAllReservationResponse: LiveData<Resource<GetAllReservationsResponse>>
        get() = _getAllReservationResponse

    fun setAllReservationResponse(
        tokenAccess: String,
    ) = viewModelScope.launch {
        _getAllReservationResponse.value = Resource.Loading
        _getAllReservationResponse.value = repository.getAllReservation(tokenAccess)
    }
}