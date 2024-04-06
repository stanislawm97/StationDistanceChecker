package org.mothdigital.station_span.domain

import org.mothdigital.station_span.domain.model.Station

interface StationSpanRepository {
    suspend fun getStation(id: Int): Station?
    suspend fun getStationByKeyword(keyword: String): List<Station>
}