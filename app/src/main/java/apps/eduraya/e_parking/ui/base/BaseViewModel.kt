package apps.eduraya.e_parking.ui.base

import androidx.lifecycle.ViewModel
import apps.eduraya.e_parking.data.repository.auth.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val repository: BaseRepository
): ViewModel() {
    suspend fun logout() = withContext(Dispatchers.IO){
        repository.logout()
    }
}