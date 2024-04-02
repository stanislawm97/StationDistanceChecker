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
import org.mothdigital.station_span.domain.usecase.CalculateDistanceUseCase

class StationSpanViewModel(
    private val stationSpanRepository: StationSpanRepository,
    private val calculateDistanceUseCase: CalculateDistanceUseCase,
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
                val distance = calculateDistanceUseCase(
                    station?.latitude,
                    station?.longitude,
                    it.selectedSecondStation?.latitude,
                    it.selectedSecondStation?.longitude,
                )
                it.copy(
                    selectedFirstStation = station,
                    distance = distance,
                )
            }
        }
    }

    fun selectSecondStation(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val station = stationSpanRepository.getStation(id)

            _stateFlow.update {
                val distance = calculateDistanceUseCase(
                    it.selectedFirstStation?.latitude,
                    it.selectedFirstStation?.longitude,
                    station?.latitude,
                    station?.longitude,
                )

                it.copy(
                    selectedSecondStation = station,
                    distance = distance,
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