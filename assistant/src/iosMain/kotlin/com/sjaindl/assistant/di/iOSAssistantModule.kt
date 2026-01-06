package com.sjaindl.assistant.di

import com.sjaindl.assistant.database.DriverFactory
import org.koin.dsl.module

actual val platformModule = module {
    single {
        DriverFactory()
    }
}
