package org.mothdigital.stationdistancechecker.stationspan.data

import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time.FetchTimeDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station.StationDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station_keyword.StationKeywordDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.remote.KoleoApi
import org.mothdigital.stationdistancechecker.stationspan.data.mapper.toDomain
import org.mothdigital.stationdistancechecker.stationspan.data.mapper.toEntity
import org.mothdigital.stationdistancechecker.stationspan.data.mapper.toFetchTimeEntity
import org.mothdigital.stationdistancechecker.stationspan.domain.StationSpanRepository
import org.mothdigital.stationdistancechecker.stationspan.domain.model.Station
import java.util.concurrent.CancellationException
import kotlin.time.Duration.Companion.hours

class StationSpanRepositoryImpl(
    private val koleoApi: KoleoApi,
    private val fetchTimeDao: FetchTimeDao,
    private val stationDao: StationDao,
    private val stationKeywordDao: StationKeywordDao,
) : StationSpanRepository {

    override suspend fun getStation(id: Int): Station? {
        runCatching {
            updateIfNeeded()
        }.onFailure {
            if (it is CancellationException) {
                throw it
            }
        }

        return stationDao.getById(id)?.toDomain()
    }

    override suspend fun getStationByKeyword(keyword: String): List<Station> {
        runCatching {
            updateIfNeeded()
        }.onFailure {
            if (it is CancellationException) {
                throw it
            }
        }

        return stationDao.findStationsByKeyword("%$keyword%").map {
            it.toDomain()
        }
    }

    private suspend fun updateIfNeeded() {
        val time = System.currentTimeMillis()
        val timeUpdate = fetchTimeDao.getFetchTime()?.time?.plus(24.hours.inWholeMilliseconds) ?: 0

        if (timeUpdate < time) {
            update(time)
        }
    }

    private suspend fun update(time: Long) {
        val stations = koleoApi.fetchStations().map {
            it.toEntity()
        }
        val stationKeywords = koleoApi.fetchStationKeywords().map {
            it.toEntity()
        }

        stationDao.deleteAll()
        stationDao.insertAll(*stations.toTypedArray())
        stationKeywordDao.deleteAll()
        stationKeywordDao.insertAll(*stationKeywords.toTypedArray())
        fetchTimeDao.setFetchTime(time.toFetchTimeEntity())
    }
}