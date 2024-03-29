package org.mothdigital.station_span.data.datasource.remote

import org.mothdigital.station_span.data.datasource.remote.model.StationDto
import org.mothdigital.station_span.data.datasource.remote.model.StationKeywordDto
import retrofit2.http.GET

interface KoleoApi {

    @GET("stations")
    suspend fun fetchStations(): List<StationDto>

    @GET("station_keywords")
    suspend fun fetchStationKeywords(): List<StationKeywordDto>
}