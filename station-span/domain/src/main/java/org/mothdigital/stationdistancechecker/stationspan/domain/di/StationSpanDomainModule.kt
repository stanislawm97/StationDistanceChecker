package org.mothdigital.stationdistancechecker.stationspan.domain.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.mothdigital.stationdistancechecker.stationspan.domain.usecase.CalculateDistanceUseCase
import org.mothdigital.stationdistancechecker.stationspan.domain.usecase.FetchStationKeywordsUseCase

val stationSpanDomainModule = module {
    singleOf(::CalculateDistanceUseCase)
    singleOf(::FetchStationKeywordsUseCase)
}