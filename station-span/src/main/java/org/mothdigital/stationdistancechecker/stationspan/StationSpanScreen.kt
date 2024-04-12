package org.mothdigital.stationdistancechecker.stationspan

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import org.mothdigital.stationdistancechecker.stationspan.components.DistanceBetweenStations
import org.mothdigital.stationdistancechecker.stationspan.components.MeasureDistanceTitle
import org.mothdigital.stationdistancechecker.stationspan.components.StationSpanSearchBar
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme

@Composable
fun StationSpanScreen(
    state: StationSpanState,
    actions: StationSpanActions,
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val isDistance by remember(state.selectedFirstStation, state.selectedSecondStation) {
            derivedStateOf {
                state.selectedFirstStation != null && state.selectedSecondStation != null
            }
        }
        val guidelineAnim by animateDpAsState(
            targetValue = if (isDistance) 150.dp else 120.dp,
            label = "",
        )
        val backgroundAnim by animateDpAsState(
            targetValue = if (isDistance) 240.dp else 220.dp,
            label = "",
        )
        
        val guideline = createGuidelineFromTop(guidelineAnim)
        val (title, firstSearchBar, distance, secondSearchBar, background, map) = createRefs()
        var firstStationSearchBarActive by rememberSaveable { mutableStateOf(false) }
        var secondStationSearchBarActive by rememberSaveable { mutableStateOf(false) }
        val polandLatLong = remember { LatLng(52.0, 19.0) }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(polandLatLong, 5.5f)
        }

        MeasureDistanceTitle(
            modifier = Modifier
                .zIndex(5f)
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        StationSpanSearchBar(
            modifier = Modifier
                .testTag("firstSearchBar")
                .zIndex(4f)
                .constrainAs(firstSearchBar) {
                    top.linkTo(title.bottom, margin = 6.dp)
                },
            hint = stringResource(id = R.string.hint_start_typing_station_name),
            iconTint = Color.Red,
            stations = state.stations,
            active = firstStationSearchBarActive,
            onActiveChange = {
                firstStationSearchBarActive = it
                if (it) {
                    secondStationSearchBarActive = false
                    actions.onQueryChange("")
                }
            },
            onQueryChange = {
                actions.onQueryChange(it)
            },
            onItemClick = {
                firstStationSearchBarActive = false
                actions.onSelectFirstStation(it)
            },
            onSearch = {
                firstStationSearchBarActive = false
            },
            onClear = {
                firstStationSearchBarActive = false
                actions.onFirstStationClear()
            }
        )

        DistanceBetweenStations(
            modifier = Modifier
                .testTag("distance")
                .zIndex(3f)
                .constrainAs(distance) {
                    bottom.linkTo(secondSearchBar.top, (-18).dp)
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end)

                    width = Dimension.fillToConstraints
                },
            isDistanceVisible = isDistance,
            distance = state.distance,
        )

        StationSpanSearchBar(
            modifier = Modifier
                .testTag("secondSearchBar")
                .zIndex(2f)
                .constrainAs(secondSearchBar) {
                    top.linkTo(guideline)
                },
            hint = stringResource(id = R.string.hint_start_typing_station_name),
            iconTint = Color.Red,
            stations = state.stations,
            active = secondStationSearchBarActive,
            onActiveChange = {
                secondStationSearchBarActive = it
                if (it) {
                    firstStationSearchBarActive = false
                    actions.onQueryChange("")
                }
            },
            onQueryChange = {
                actions.onQueryChange(it)
            },
            onItemClick = {
                secondStationSearchBarActive = false
                actions.onSelectSecondStation(it)
            },
            onSearch = {
                secondStationSearchBarActive = false
            },
            onClear = {
                secondStationSearchBarActive = false
                actions.onSecondStationClear()
            }
        )

        Box(
            modifier = Modifier
                .height(backgroundAnim)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .zIndex(1f)
                .constrainAs(background) {
                    top.linkTo(parent.top)
                },
        )

        GoogleMap(
            modifier = Modifier
                .constrainAs(map) {
                    top.linkTo(background.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            cameraPositionState = cameraPositionState,
        ) {
            val isFirstStationDrawable =
                state.selectedFirstStation?.latitude != null && state.selectedFirstStation.longitude != null
            val isSecondStationDrawable =
                state.selectedSecondStation?.latitude != null && state.selectedSecondStation.longitude != null

            if (isFirstStationDrawable && isSecondStationDrawable) {
                Marker(
                    state = remember(state.selectedFirstStation) {
                        MarkerState(
                            LatLng(
                                state.selectedFirstStation!!.latitude!!,
                                state.selectedFirstStation.longitude!!
                            )
                        )
                    },
                    title = state.selectedFirstStation!!.name,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                )

                Marker(
                    state = remember(state.selectedSecondStation) {
                        MarkerState(
                            LatLng(
                                state.selectedSecondStation!!.latitude!!,
                                state.selectedSecondStation.longitude!!
                            )
                        )
                    },
                    title = state.selectedSecondStation!!.name,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                )

                Polyline(
                    points = listOf(
                        LatLng(
                            state.selectedFirstStation.latitude!!,
                            state.selectedFirstStation.longitude!!
                        ),
                        LatLng(
                            state.selectedSecondStation.latitude!!,
                            state.selectedSecondStation.longitude!!
                        )
                    ),
                    color = Color.Red,
                )
            }
        }
    }
}

@Composable
@Preview
private fun StationSpanScreenPreview() {
    StationDistanceCheckerTheme {
        StationSpanScreen(
            state = StationSpanState(),
            actions = StationSpanActions()
        )
    }
}

