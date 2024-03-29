package org.mothdigital.stationdistancechecker

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.mothdigital.station_span.data.di.stationSpanDataModule
import org.mothdigital.station_span.di.stationSpanModule
import org.mothdigital.stationdistancechecker.di.mainApplicationModule

class MainApplication  : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@MainApplication)
            modules(mainApplicationModule, stationSpanModule, stationSpanDataModule)
        }
    }
}