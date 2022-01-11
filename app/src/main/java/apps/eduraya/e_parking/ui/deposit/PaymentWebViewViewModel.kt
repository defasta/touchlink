package apps.eduraya.e_parking.ui.deposit

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentWebViewViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
):BaseViewModel(repository){

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
    }

    private val _tokenDeposit = savedStateHandle.getLiveData<String>(KEY_TOKEN)
    val tokenDeposit : LiveData<String>
        get() = _tokenDeposit
}