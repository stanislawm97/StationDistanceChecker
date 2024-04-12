package org.mothdigital.stationdistancechecker.stationspan.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.mothdigital.stationdistancechecker.stationspan.domain.model.Station
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme

@Composable
fun StationListItem(
    modifier: Modifier = Modifier,
    station: Station,
    onClick: () -> Unit,
) {
    Box(modifier) {
        ListItem(
            headlineContent = { Text(station.name.toString()) },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                )
            },
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Preview(name = "StationListItem")
@Composable
private fun PreviewStationListItem() {
    StationDistanceCheckerTheme {
        StationListItem(
            station = Station(
                city = null,
                country = null,
                hasAnnouncements = null,
                hits = 1,
                ibnr = null,
                id = 1,
                isGroup = null,
                isNearbyStationEnabled = null,
                latitude = 0.0,
                localisedName = null,
                longitude = 0.0,
                name = "Keyword",
                nameSlug = null,
                region = null,
            ),
            onClick = {}
        )
    }
}