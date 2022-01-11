package apps.eduraya.e_parking.ui.deposit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import apps.eduraya.e_parking.data.network.Resource
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.data.responses.CreateDepositResponse
import apps.eduraya.e_parking.data.responses.DataDeposit
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDepositViewModel @Inject constructor(
    private val repository: AppsRepository
):BaseViewModel(repository) {

    private val _createDepositResponse: MutableLiveData<Resource<CreateDepositResponse>> = MutableLiveData()
    val createDepositResponse: LiveData<Resource<CreateDepositResponse>>
        get() = _createDepositResponse

    fun createDeposit(
        token: String,
        nominal: String
    ) = viewModelScope.launch {
        _createDepositResponse.value = Resource.Loading
        _createDepositResponse.value = repository.createDeposit(token,nominal)
    }

}