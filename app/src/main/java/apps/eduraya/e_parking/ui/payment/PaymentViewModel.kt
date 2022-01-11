package apps.eduraya.e_parking.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.deposit.GetDepositResponse
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: AppsRepository
):BaseViewModel(repository){

    private val _getAllDepositResponse: MutableLiveData<Resource<GetDepositResponse>> = MutableLiveData()
    val getAllDepositResponse: LiveData<Resource<GetDepositResponse>>
        get() = _getAllDepositResponse

    fun setAllDepositResponse(
        token: String
    ) = viewModelScope.launch {
        _getAllDepositResponse.value = Resource.Loading
        _getAllDepositResponse.value = repository.getAllDeposit(token)
    }

}