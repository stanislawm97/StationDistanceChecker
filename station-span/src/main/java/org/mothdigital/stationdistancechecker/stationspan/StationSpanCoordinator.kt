package org.mothdigital.stationdistancechecker.stationspan

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

    fun onQueryChange(query: String) {
        viewModel.fetchStationKeywords(query)
    }

    fun onSelectFirstStation(id: Int) {
        viewModel.selectFirstStation(id)
    }

    fun onSelectSecondStation(id: Int) {
        viewModel.selectSecondStation(id)
    }

    fun onFirstStationClear() {
        viewModel.clearFirstStation()
    }

    fun onSecondStationClear() {
        viewModel.clearSecondStation()
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