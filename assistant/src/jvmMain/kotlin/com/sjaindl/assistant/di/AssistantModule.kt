@file:JvmName("JvmAssistantModule")

package com.sjaindl.assistant.di

import com.sjaindl.assistant.database.DriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        DriverFactory()
    }
}
