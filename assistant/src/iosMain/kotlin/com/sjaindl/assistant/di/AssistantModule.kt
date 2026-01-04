@file:JvmName("IosAssistantModule")

package com.sjaindl.assistant.di

import com.sjaindl.assistant.database.DriverFactory
import org.koin.dsl.module
import kotlin.jvm.JvmName

actual val platformModule = module {
    single {
        DriverFactory()
    }
}
