package org.mothdigital.station_span

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import org.mothdigital.station_span.components.StationSpanSearchBar

@Composable
fun StationSpanScreen(
    state: StationSpanState,
    actions: StationSpanActions,
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val guideline = createGuidelineFromTop(0.25f)
        val (searchBar1, searchBar2) = createRefs()

        StationSpanSearchBar(
            modifier = Modifier
                .padding(8.dp)
                .zIndex(2f)
                .constrainAs(searchBar1) {
                    top.linkTo(parent.top, margin = 16.dp)
                },
            stationKeywords = state.stationKeyword,
            onQueryChange = {
                actions.onQueryChange(it)
            },
            onItemClick = {

            }
        )

        StationSpanSearchBar(
            modifier = Modifier
                .padding(8.dp)
                .zIndex(1f)
                .constrainAs(searchBar2) {
                    top.linkTo(guideline, margin = 16.dp)
                },
            stationKeywords = state.stationKeyword,
            onQueryChange = {
                actions.onQueryChange(it)
            },
            onItemClick = {

            }
        )
    }
}

@Composable
@Preview(name = "StationSpan")
private fun StationSpanScreenPreview() {
    StationSpanScreen(
        state = StationSpanState(),
        actions = StationSpanActions()
    )
}

