package com.hasan.dnb

import GoogleAuthProvider
import androidx.credentials.CredentialManager
import com.hasan.dnb.auth.AuthRepository
import data.AuthRepositoryImpl
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { CredentialManager.create(androidContext()) }
        single { GoogleAuthProvider(get()) }
        single<AuthRepository> { AuthRepositoryImpl() }
    }