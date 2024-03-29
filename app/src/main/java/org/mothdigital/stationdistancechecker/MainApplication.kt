package org.mothdigital.stationdistancechecker

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.mothdigital.stationdistancechecker.di.mainApplicationModule

class MainApplication  : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@MainApplication)
            modules(mainApplicationModule)
        }
    }
}