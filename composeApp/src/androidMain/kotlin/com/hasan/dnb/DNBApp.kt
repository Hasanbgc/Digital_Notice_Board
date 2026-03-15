package com.hasan.dnb

import android.app.Application
import com.hasan.dnb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DNBApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DNBApp)
            modules(
                platformModule,
                sharedModule,
                viewModelModule
            )
        }
    }

}