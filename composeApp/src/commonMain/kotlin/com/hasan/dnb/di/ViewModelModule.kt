package com.hasan.dnb.di



import Profile.ProfileViewModel
import Settings.SettingsViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import create.CreateNoticeViewModel
import home.HomeViewModel
import login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import registration.RegistrationViewModel

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::CreateNoticeViewModel)
}