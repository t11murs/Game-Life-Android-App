package com.example.fatcatproject.Menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import auth.remote.model.response.CoinsResponse
import auth.remote.service.HttpService
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MenuViewModule (private val httpService: HttpService
) : ViewModel() {
    private val _flow = MutableSharedFlow<CoinsResponse>()
    val flow: SharedFlow<CoinsResponse> = _flow

    fun getCoins(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = httpService.getCoins().body<CoinsResponse>()
                _flow.emit(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}