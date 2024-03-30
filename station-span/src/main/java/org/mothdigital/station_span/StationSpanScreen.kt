package org.mothdigital.station_span

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import org.mothdigital.station_span.components.StationSpanSearchBar
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme
import org.mothdigital.stationdistancechecker.station_span.R
import kotlin.math.roundToLong


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
        val (title, searchBar1, distance, searchBar2, background, map) = createRefs()
        var searchBar1Active by rememberSaveable { mutableStateOf(false) }
        var searchBar2Active by rememberSaveable { mutableStateOf(false) }

        Text(
            modifier = Modifier
                .zIndex(5f)
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Zmierz odleglosc pomiędzy stacjami",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        StationSpanSearchBar(
            modifier = Modifier
                .zIndex(4f)
                .constrainAs(searchBar1) {
                    top.linkTo(title.bottom, margin = 6.dp)
                },
            hint = stringResource(id = R.string.hint_start_typing_station_name),
            iconTint = Color.Red,
            stationKeywords = state.stationKeyword,
            active = searchBar1Active,
            onActiveChange = {
                if (it) {
                    searchBar2Active = false
                }
            },
            onQueryChange = {
                searchBar1Active = it.isNotEmpty()
                actions.onQueryChange(it)
            },
            onItemClick = {
                searchBar1Active = false
                actions.onSelectFirstStation(it)
            },
            onSearch = {
                searchBar1Active = false
            },
            onClear = {
                searchBar1Active = false
                actions.onFirstStationClear()
            }
        )

        AnimatedVisibility(
            modifier = Modifier
                .zIndex(3f)
                .constrainAs(distance) {
                    bottom.linkTo(searchBar2.top, (-18).dp)
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end)

                    width = Dimension.fillToConstraints
                },
            visible = isDistance,
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(id = R.drawable.heights),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                )
                Text(
                    text = "${state.distance.roundToKm()} km",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }


        StationSpanSearchBar(
            modifier = Modifier
                .zIndex(2f)
                .constrainAs(searchBar2) {
                    top.linkTo(guideline)
                },
            hint = stringResource(id = R.string.hint_start_typing_station_name),
            iconTint = Color.Red,
            stationKeywords = state.stationKeyword,
            active = searchBar2Active,
            onActiveChange = {
                if (it) {
                    searchBar1Active = false
                }
            },
            onQueryChange = {
                searchBar2Active = it.isNotEmpty()
                actions.onQueryChange(it)
            },
            onItemClick = {
                searchBar2Active = false
                actions.onSelectSecondStation(it)
            },
            onSearch = {
                searchBar2Active = false
            },
            onClear = {
                searchBar2Active = false
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

        val singapore = LatLng(52.0, 19.0)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 5.5f)
        }
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
            val first =
                state.selectedFirstStation?.latitude != null && state.selectedFirstStation.longitude != null
            val second =
                state.selectedSecondStation?.latitude != null && state.selectedSecondStation.longitude != null

            if (first && second) {
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

@JvmInline
value class Meters(val value: Long) {
    override fun toString(): String {
        return value.toString()
    }
}

fun Long.toMeters() = Meters(this)

@JvmInline
value class Kilometers(val value: Long) {
    override fun toString(): String {
        return value.toString()
    }
}

private fun Meters.roundToKm(): Kilometers =
    Kilometers((value / 1000.0).roundToLong())

@Composable
@Preview(name = "StationSpan")
private fun StationSpanScreenPreview() {
    StationDistanceCheckerTheme {
        StationSpanScreen(
            state = StationSpanState(),
            actions = StationSpanActions()
        )
    }
}

