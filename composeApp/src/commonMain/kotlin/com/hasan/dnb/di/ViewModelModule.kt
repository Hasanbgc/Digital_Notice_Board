package com.hasan.dnb.di



import authScreen.AuthViewModel
import com.hasan.dnb.auth.AuthRepository
import Profile.ProfileViewModel
import Settings.SettingsViewModel
import com.hasan.dnb.app.AppViewModel
import createNotice.CreateNoticeViewModel
import home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel { AuthViewModel(get<AuthRepository>()) }
    viewModel {(uid:String) ->
        HomeViewModel(uid)
    }
    viewModelOf(::ProfileViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::CreateNoticeViewModel)

    viewModel { AppViewModel(get<AuthRepository>()) }

    // viewModelOf(::LoginViewModel)
    // viewModelOf(::RegistrationViewModel)
}