package auth.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import auth.remote.model.Result
import auth.remote.model.request.RegistrationRequest
import auth.remote.model.response.LoginResponse
import auth.remote.service.HttpService
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
class  RegistrationViewModel(
    private val httpService: HttpService
) : ViewModel() {
    private val _flow = MutableSharedFlow<Result<LoginResponse>>()
    val flow: SharedFlow<Result<LoginResponse>> = _flow


    fun register(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = httpService.register(RegistrationRequest(email, password, name)).body<LoginResponse>()
                _flow.emit(Result.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                _flow.emit(Result.Error(e))
            }
        }
    }
}