package com.sjaindl.app.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            modules = appModule,
        )
    }
}
