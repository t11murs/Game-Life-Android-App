package com.example.fatcatproject.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import auth.remote.model.Result
import auth.remote.model.response.UserResponse
import auth.remote.service.HttpService
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class  AccountViewModel(
    private val httpService: HttpService
) : ViewModel() {
    private val _flow = MutableSharedFlow<Result<UserResponse>>()
    val flow: SharedFlow<Result<UserResponse>> = _flow
    fun getSelfProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = httpService.getSelfProfile().body<UserResponse>()
                _flow.emit(Result.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                _flow.emit(Result.Error(e))
            }
        }
    }
}