package com.hasan.dnb

import com.hasan.dnb.di.viewModelModule
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(
            platformModule,
            sharedModule,
            viewModelModule
        )
    }
}