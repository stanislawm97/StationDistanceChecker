package org.mothdigital.stationdistancechecker.stationspan.domain

import org.mothdigital.stationdistancechecker.stationspan.domain.model.Station

interface StationSpanRepository {
    suspend fun getStation(id: Int): Station?
    suspend fun getStationByKeyword(keyword: String): List<Station>
}