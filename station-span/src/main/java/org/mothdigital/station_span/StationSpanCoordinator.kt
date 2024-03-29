package org.mothdigital.station_span

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.androidx.compose.koinViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class StationSpanCoordinator(
    val viewModel: StationSpanViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    init {
        viewModel.fetchStations()
    }

    fun doStuff() {
        // TODO Handle UI Action
    }

    fun onQueryChange(query: String) {
        viewModel.fetchStationKeywords(query)
    }
}

@Composable
fun rememberStationSpanCoordinator(
    viewModel: StationSpanViewModel = koinViewModel()
): StationSpanCoordinator {
    return remember(viewModel) {
        StationSpanCoordinator(
            viewModel = viewModel
        )
    }
}