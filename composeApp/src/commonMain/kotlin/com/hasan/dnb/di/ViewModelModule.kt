package com.hasan.dnb.di



import authScreen.AuthViewModel
import com.hasan.dnb.auth.AuthRepository
import Profile.ProfileViewModel
import Settings.SettingsViewModel
import com.hasan.dnb.app.AppViewModel
import com.hasan.dnb.domain.UserSession
import createNotice.CreateNoticeViewModel
import home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::CreateNoticeViewModel)

    viewModel {(userSession: UserSession) -> HomeViewModel(userSession) }
    viewModel { AuthViewModel(get<AuthRepository>()) }
    viewModel { (userSession: UserSession) -> ProfileViewModel(userSession,get<AuthRepository>())}
    viewModel { AppViewModel(get<AuthRepository>()) }
}