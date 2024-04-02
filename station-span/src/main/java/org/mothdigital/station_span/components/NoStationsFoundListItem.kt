package org.mothdigital.station_span.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme
import org.mothdigital.stationdistancechecker.station_span.R

@Composable
fun NoStationsFoundListItem(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        ListItem(
            headlineContent = {
                Text(stringResource(id = R.string.no_stations_found))
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                )
            },
        )
    }
}

@Preview(name = "NoStationsFoundListItem")
@Composable
private fun PreviewNoStationsFoundListItem() {
    StationDistanceCheckerTheme {
        NoStationsFoundListItem()
    }
}