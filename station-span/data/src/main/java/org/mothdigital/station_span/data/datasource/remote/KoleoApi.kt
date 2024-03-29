package org.mothdigital.station_span.data.datasource.remote

import retrofit2.http.GET

interface KoleoApi {

    @GET("stations")
    suspend fun fetchStations(): List<StationDto>

    @GET("station_keywords")
    suspend fun fetchStationKeywords(): List<StationKeywordDto>
}