package org.mothdigital.station_span

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mothdigital.station_span.domain.StationSpanRepository
import org.mothdigital.station_span.domain.model.Meters
import org.mothdigital.station_span.domain.model.toMeters
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class StationSpanViewModel(
    private val stationSpanRepository: StationSpanRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(StationSpanState())
    val stateFlow: StateFlow<StationSpanState> = _stateFlow.asStateFlow()

    private var fetchStationKeywordsJob: Job? = null

    fun fetchStationKeywords(query: String) {
        fetchStationKeywordsJob?.cancel()
        fetchStationKeywordsJob = viewModelScope.launch(Dispatchers.IO) {
            val stationKeywords = runCatching {
                stationSpanRepository.getStationKeyword(query)
            }.onFailure {
                if(it is CancellationException) {
                    throw it
                } else {
                    it.printStackTrace()
                }
            }.getOrDefault(emptyList())

            _stateFlow.update {
                it.copy(stationKeyword = stationKeywords)
            }
        }
    }

    fun selectFirstStation(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val station = stationSpanRepository.getStation(id)

            _stateFlow.update {
                it.copy(
                    selectedFirstStation = station,
                    distance = if (station != null && it.selectedSecondStation != null) {
                        calculateDistance(
                            station.latitude,
                            station.longitude,
                            it.selectedSecondStation.latitude,
                            it.selectedSecondStation.longitude,
                        ).toLong().toMeters()
                    } else {
                        Meters(0L)
                    }
                )
            }
        }
    }

    fun selectSecondStation(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val station = stationSpanRepository.getStation(id)

            _stateFlow.update {
                it.copy(
                    selectedSecondStation = station,
                    distance = if (station != null && it.selectedFirstStation != null) {
                        Meters(
                            calculateDistance(
                                station.latitude,
                                station.longitude,
                                it.selectedFirstStation.latitude,
                                it.selectedFirstStation.longitude,
                            ).toLong()
                        )
                    } else {
                        Meters(0L)
                    }
                )
            }
        }
    }

    fun clearFirstStation() {
        _stateFlow.update {
            it.copy(
                selectedFirstStation = null,
                distance = Meters(0L),
            )
        }
    }

    fun clearSecondStation() {
        _stateFlow.update {
            it.copy(
                selectedSecondStation = null,
                distance = Meters(0L),
            )
        }
    }


}

/**
 * Calculates the distance between two geographical points.
 *
 * This function uses the Haversine formula to calculate the great-circle distance between two points
 * on the Earth's surface, given their latitude and longitude in degrees.
 *
 * @param lat1 Latitude of the first point in degrees.
 * @param lon1 Longitude of the first point in degrees.
 * @param lat2 Latitude of the second point in degrees.
 * @param lon2 Longitude of the second point in degrees.
 * @return The distance between the two points in meters.
 */
fun calculateDistance(lat1: Double?, lon1: Double?, lat2: Double?, lon2: Double?): Double {
    if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
        return 0.0
    }

    val earthRadius = 6371e3

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c
}