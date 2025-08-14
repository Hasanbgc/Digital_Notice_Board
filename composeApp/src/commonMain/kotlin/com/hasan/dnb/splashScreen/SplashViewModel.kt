package com.hasan.dnb.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    val _state =  MutableStateFlow<SplashScreenAction>(SplashScreenAction.Loading)
    val state: StateFlow<SplashScreenAction> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            setState(SplashScreenAction.Complete)
        }
    }

    fun setState(action: SplashScreenAction){
        when(action){
            is SplashScreenAction.Loading ->{
                _state.value = SplashScreenAction.Loading
            }
            is SplashScreenAction.Complete -> {
                _state.value = SplashScreenAction.Complete
            }
        }
    }
}