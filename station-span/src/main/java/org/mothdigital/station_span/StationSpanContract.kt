package org.mothdigital.station_span

import org.mothdigital.station_span.domain.model.Station
import org.mothdigital.station_span.domain.model.StationKeyword
import org.mothdigital.station_span.domain.model.Meters


/**
 * UI State that represents StationSpanScreen
 **/
data class StationSpanState(
    val stations: List<Station> = emptyList(),
    val selectedFirstStation: Station? = null,
    val selectedSecondStation: Station? = null,
    val distance: Meters = Meters(0L),
)

/**
 * StationSpan Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class StationSpanActions(
    val onSelectFirstStation: (Int) -> Unit = {},
    val onSelectSecondStation: (Int) -> Unit = {},
    val onQueryChange: (String) -> Unit = {},
    val onFirstStationClear: () -> Unit = {},
    val onSecondStationClear: () -> Unit = {},
)