package apps.eduraya.e_parking.ui.deposit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.DataDeposit
import apps.eduraya.e_parking.data.responses.DataPayDeposit
import apps.eduraya.e_parking.data.responses.PayDepositResponse
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayDepositViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
):BaseViewModel(repository){

    companion object {
        const val KEY_ID = "KEY_ID"
        const val KEY_AMOUNT = "KEY_AMOUNT"
        const val KEY_STATUS = "KEY_STATUS"
    }

    private val _idDeposit = savedStateHandle.getLiveData<String>(KEY_ID)
    val idDeposit : LiveData<String>
        get() = _idDeposit

    private val _amountDeposit = savedStateHandle.getLiveData<String>(KEY_AMOUNT)
    val amountDeposit : LiveData<String>
        get() = _amountDeposit

    private val _statusDeposit = savedStateHandle.getLiveData<String>(KEY_STATUS)
    val statusDeposit : LiveData<String>
        get() = _statusDeposit

    private val _payDepositResponse: MutableLiveData<Resource<PayDepositResponse>> = MutableLiveData()
    val payDepositResponse: LiveData<Resource<PayDepositResponse>>
        get() = _payDepositResponse

    fun payDeposit(
        tokenAccess: String,
        id: String
    ) = viewModelScope.launch {
        _payDepositResponse.value = Resource.Loading
        _payDepositResponse.value = repository.getTokenDeposit(tokenAccess, id)
    }
}