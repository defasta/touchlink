package apps.eduraya.e_parking.ui.my_qr

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import apps.eduraya.e_parking.data.repository.AppsRepository
import apps.eduraya.e_parking.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyQrViewModel @Inject constructor(
    private val repository: AppsRepository,
    savedStateHandle: SavedStateHandle
):BaseViewModel(repository){

    companion object{
        const val KEY_ACTION = "KEY_ACTION"
    }

    private val _keyAction = savedStateHandle.getLiveData<String>(KEY_ACTION)
    val keyAction: LiveData<String>
        get() =  _keyAction

}