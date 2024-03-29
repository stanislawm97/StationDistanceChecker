package org.mothdigital.stationdistancechecker

import org.junit.jupiter.api.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify
import org.mothdigital.station_span.data.di.stationSpanDataModule
import org.mothdigital.stationdistancechecker.di.mainApplicationModule

@OptIn(KoinExperimentalAPI::class)
class CheckModulesTest {
    @Test
    fun checkMainApplicationModule() {
        mainApplicationModule.verify()
    }

    @Test
    fun checkStationSpanDataModule() {
        stationSpanDataModule.verify()
    }
}