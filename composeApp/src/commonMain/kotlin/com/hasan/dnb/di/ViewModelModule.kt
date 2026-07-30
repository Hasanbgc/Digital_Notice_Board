package com.hasan.dnb.di



import authScreen.AuthViewModel
import com.hasan.dnb.auth.AuthRepository
import com.hasan.dnb.location.LocationSource
import com.hasan.dnb.notice.NoticeRepository
import Profile.ProfileViewModel
import Settings.SettingsViewModel
import com.hasan.dnb.app.AppViewModel
import com.hasan.dnb.auth.UserRepository
import com.hasan.dnb.domain.UserSession
import createNotice.CreateNoticeViewModel
import domain.Repository
import home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SettingsViewModel)

    viewModel {(userSession: UserSession) -> HomeViewModel(userSession, get<NoticeRepository>(), get<LocationSource>()) }
    viewModel { AuthViewModel(get<AuthRepository>(),get <UserRepository>()) }
    viewModel { ProfileViewModel(get<Repository>(),get<AuthRepository>())}
    viewModel { AppViewModel(get<AuthRepository>()) }
    viewModel { CreateNoticeViewModel(get<NoticeRepository>(), get<LocationSource>()) }
}