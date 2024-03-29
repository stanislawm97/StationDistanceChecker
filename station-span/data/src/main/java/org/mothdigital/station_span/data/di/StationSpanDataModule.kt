package org.mothdigital.station_span.data.di

import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.mothdigital.station_span.data.StationSpanRepositoryImpl
import org.mothdigital.station_span.data.datasource.remote.KoleoApi
import org.mothdigital.station_span.domain.StationSpanRepository
import retrofit2.Retrofit

val stationSpanDataModule = module {
    single<KoleoApi> {
        get<Retrofit>().create(KoleoApi::class.java)
    }

    singleOf(::StationSpanRepositoryImpl) bind StationSpanRepository::class
}