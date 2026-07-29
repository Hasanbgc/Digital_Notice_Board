package com.hasan.dnb.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasan.dnb.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.AppDestination

class AppViewModel(private val authRepository: AuthRepository): ViewModel() {
    private val _destination = MutableStateFlow<AppDestination?>(null)
    val destination = _destination.asStateFlow()

    init {
       observeAuthState()
    }

    private fun observeAuthState(){
        viewModelScope.launch {
            authRepository
                .observeAuthState()
                .distinctUntilChanged()
                .collect { userSession ->
                if (userSession.idToken.isNotEmpty()) {
                    _destination.update {
                        AppDestination.Main(userSession)
                    }
                } else {
                    _destination.update {
                        AppDestination.Auth
                    }
                }
            }
        }

    }



}