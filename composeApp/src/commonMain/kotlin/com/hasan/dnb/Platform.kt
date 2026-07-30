package com.hasan.dnb

import androidx.compose.runtime.Composable
import com.hasan.dnb.auth.UserRepository
import com.hasan.dnb.session.SessionManager
import com.hasan.dnb.session.SessionStorage
import com.hasan.dnb.session.SettingsSessionStorage
import com.russhwolf.settings.Settings
import data.HttpClientFactory
import data.RepositoryImpl
import data.UserRepositoryImpl
import domain.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<Repository>{ RepositoryImpl(get()) }
    single { Settings() }
    single<SessionStorage> { SettingsSessionStorage(get()) }
    single<SessionManager>{ SessionManager(get()) }
}


