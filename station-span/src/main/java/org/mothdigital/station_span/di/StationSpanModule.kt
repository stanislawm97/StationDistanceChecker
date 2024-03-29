package org.mothdigital.station_span.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.mothdigital.station_span.StationSpanViewModel

val stationSpanModule = module {
    viewModelOf(::StationSpanViewModel)
}