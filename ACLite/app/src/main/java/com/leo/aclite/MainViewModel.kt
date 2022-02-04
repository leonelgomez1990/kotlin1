package com.leo.aclite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<UiState>(UiState())
    val state: LiveData<UiState> get() = _state

    data class UiState (
        val loggingIn: Boolean = false,
        val loggedIn: Boolean = false,
        val userError: Int? = null,
        val passError: Int? = null
    )



    //la vista deberia informarnos que ha ocurrido en la vista
    //ej onUserClick, onItemClick
    fun onTryLogin(user: String, pass: String) {
        viewModelScope.launch {
            _state.value = UiState(loggingIn = true)
            tryLogin(user, pass)
        }
    }

    private suspend fun tryLogin(username: String, password: String) {
        delay(2000)
        val userError = if(!username.contains('@')) R.string.user_error else null
        val passError = if(password.length < 5) R.string.pass_error else null
        val loggedIn = userError == null && passError == null
        _state.value = UiState(
            loggedIn = loggedIn,
            userError = userError,
            passError = passError
        )
    }

    fun onNavigateToNextScreen() {
        _state.value = requireNotNull(_state.value).copy(loggedIn = false)
    }
}