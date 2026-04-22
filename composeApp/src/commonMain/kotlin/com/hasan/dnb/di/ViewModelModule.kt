package com.hasan.dnb.di



import AuthScreen.AuthViewModel
import Profile.ProfileViewModel
import Settings.SettingsViewModel
import createNotice.CreateNoticeViewModel
import home.HomeViewModel
import login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import registration.RegistrationViewModel

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AuthViewModel)
    viewModel {(uid:String) ->
        HomeViewModel(uid)
    }
    viewModelOf(::ProfileViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::CreateNoticeViewModel)

    // viewModelOf(::LoginViewModel)
    // viewModelOf(::RegistrationViewModel)
}