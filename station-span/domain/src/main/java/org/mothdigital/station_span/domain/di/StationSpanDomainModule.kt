package org.mothdigital.station_span.domain.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.mothdigital.station_span.domain.usecase.CalculateDistanceUseCase
import org.mothdigital.station_span.domain.usecase.FetchStationKeywordsUseCase

val stationSpanDomainModule = module {
    singleOf(::CalculateDistanceUseCase)
    singleOf(::FetchStationKeywordsUseCase)
}