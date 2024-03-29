package org.mothdigital.station_span

import org.mothdigital.station_span.domain.model.Station
import org.mothdigital.station_span.domain.model.StationKeyword


/**
 * UI State that represents StationSpanScreen
 **/
data class StationSpanState(
    val stations: List<Station> = emptyList(),
    val stationKeyword: List<StationKeyword> = emptyList(),
)

/**
 * StationSpan Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class StationSpanActions(
    val onClick: () -> Unit = {},
    val onQueryChange: (String) -> Unit = {},
)