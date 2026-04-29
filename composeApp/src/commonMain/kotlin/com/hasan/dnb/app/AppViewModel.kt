package com.hasan.dnb.app

import androidx.lifecycle.ViewModel
import com.hasan.dnb.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import presentation.AppDestination

class AppViewModel(private val authRepository: AuthRepository): ViewModel() {
    private val _destination = MutableStateFlow<AppDestination?>(null)
    val destination = _destination.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        val uid = authRepository.checkUserSession()

        _destination.value = if (uid.isEmpty()) {
            AppDestination.Auth
        } else {
            AppDestination.Main(uid)
        }
    }

}