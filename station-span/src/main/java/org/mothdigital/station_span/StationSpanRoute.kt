package org.mothdigital.station_span

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun StationSpanRoute(
    coordinator: StationSpanCoordinator = rememberStationSpanCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(StationSpanState())

    // UI Actions
    val actions = rememberStationSpanActions(coordinator)

    // UI Rendering
    StationSpanScreen(uiState, actions)
}


@Composable
fun rememberStationSpanActions(coordinator: StationSpanCoordinator): StationSpanActions {
    return remember(coordinator) {
        StationSpanActions(
            onClick = coordinator::doStuff,
            onQueryChange = coordinator::onQueryChange
        )
    }
}