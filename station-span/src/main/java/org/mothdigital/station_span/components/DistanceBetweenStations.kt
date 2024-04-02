package org.mothdigital.station_span.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.mothdigital.station_span.domain.model.Meters
import org.mothdigital.station_span.domain.model.roundToKm
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme
import org.mothdigital.stationdistancechecker.station_span.R

@Composable
fun DistanceBetweenStations(
    modifier: Modifier = Modifier,
    isDistanceVisible: Boolean,
    distance: Meters,
) {
    Box(modifier) {
        AnimatedVisibility(
            modifier = Modifier,
            visible = isDistanceVisible,
            enter = expandVertically(),
            exit = shrinkVertically(),
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
                    text = "${distance.roundToKm()} km",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview(name = "DistanceBetweenStations")
@Composable
private fun PreviewDistanceBetweenStations() {
    StationDistanceCheckerTheme {
        DistanceBetweenStations(
            isDistanceVisible = true,
            distance = Meters(1000),
        )
    }
}