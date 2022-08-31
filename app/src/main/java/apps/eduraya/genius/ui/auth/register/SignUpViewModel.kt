package apps.eduraya.genius.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import apps.eduraya.genius.data.network.Resource
import apps.eduraya.genius.data.repository.AppsRepository
import apps.eduraya.genius.data.responses.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AppsRepository
): ViewModel(){

    private val _registerResponse: MutableLiveData<Resource<SignUpResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<SignUpResponse>>
        get() = _registerResponse

    fun signup(
        name: RequestBody,
        email: RequestBody,
        birthDay: RequestBody,
        birthPlace: RequestBody,
        address: RequestBody,
        educationalLevel: RequestBody,
        password: RequestBody
    ) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.signUp(name, email, birthDay, birthPlace, address, educationalLevel, password)
    }

    private val _datePicked : MutableLiveData<String> = MutableLiveData()
    val datePicked: LiveData<String>
        get() = _datePicked

    fun setDatePicked(date:String){
        _datePicked.postValue(date)
    }

    private val _imagePathPicked : MutableLiveData<String> = MutableLiveData()
    val imagePathPicked: LiveData<String>
        get() = _imagePathPicked

    fun setImagePathPicked(imagePath:String){
        _imagePathPicked.postValue(imagePath)
    }

}