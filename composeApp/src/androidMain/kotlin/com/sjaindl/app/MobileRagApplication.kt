package com.sjaindl.app

import android.app.Application
import com.sjaindl.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MobileRagApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MobileRagApplication)
            modules(appModule)
        }
    }
}
