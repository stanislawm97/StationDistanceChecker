package org.mothdigital.station_span

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mothdigital.station_span.domain.StationSpanRepository

class StationSpanViewModel(
    private val stationSpanRepository: StationSpanRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(StationSpanState())
    val stateFlow: StateFlow<StationSpanState> = _stateFlow.asStateFlow()

    fun fetchStations() {
        viewModelScope.launch(Dispatchers.IO) {
            val stations = runCatching {
                stationSpanRepository.getStations(intArrayOf(0, 1))
            }.onFailure {
                if(it is CancellationException) {
                    throw it
                } else {
                    it.printStackTrace()
                }
            }.getOrDefault(emptyList())

            _stateFlow.update {
                it.copy(stations = stations)
            }
        }
    }

    fun fetchStationKeywords(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
}