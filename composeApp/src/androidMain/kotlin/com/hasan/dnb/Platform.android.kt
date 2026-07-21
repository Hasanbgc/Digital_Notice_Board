package com.hasan.dnb

import GoogleAuthProvider
import androidx.credentials.CredentialManager
import androidx.room.Room
import com.hasan.dnb.auth.AuthRepository
import com.hasan.dnb.location.LocationSource
import com.hasan.dnb.notice.NoticeRepository
import data.AuthRepositoryImpl
import data.LocationSourceImpl
import data.NoticeDatabase
import data.NoticeRepositoryImpl
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

        single {
            Room.databaseBuilder(
                androidContext(),
                NoticeDatabase::class.java,
                "notice_database.db"
            )
                // No migration path exists yet; safe to do while the schema is still moving.
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
        }
        single { get<NoticeDatabase>().noticeDao() }
        single<NoticeRepository> { NoticeRepositoryImpl(get()) }
        single<LocationSource> { LocationSourceImpl(androidContext()) }
    }