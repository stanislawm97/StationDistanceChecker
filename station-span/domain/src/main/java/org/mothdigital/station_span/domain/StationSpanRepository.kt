package org.mothdigital.station_span.domain

import org.mothdigital.station_span.domain.model.Station
import org.mothdigital.station_span.domain.model.StationKeyword

interface StationSpanRepository {
    suspend fun getStations(ids: IntArray): List<Station>
    suspend fun getStationKeyword(query: String): List<StationKeyword>
}