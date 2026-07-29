package com.hasan.dnb

import androidx.compose.runtime.Composable
import com.hasan.dnb.auth.UserRepository
import data.HttpClientFactory
import data.UserRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}


