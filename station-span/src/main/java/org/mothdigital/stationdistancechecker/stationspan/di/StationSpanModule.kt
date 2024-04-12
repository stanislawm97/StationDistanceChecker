package org.mothdigital.stationdistancechecker.stationspan.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.mothdigital.stationdistancechecker.stationspan.StationSpanViewModel

val stationSpanModule = module {
    viewModelOf(::StationSpanViewModel)
}