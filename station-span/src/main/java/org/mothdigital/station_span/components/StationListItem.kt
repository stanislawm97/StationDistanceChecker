package org.mothdigital.station_span.components

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
import org.mothdigital.station_span.domain.model.StationKeyword
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme

@Composable
fun StationListItem(
    modifier: Modifier = Modifier,
    stationKeyword: StationKeyword,
    onClick: () -> Unit,
) {
    Box(modifier) {
        ListItem(
            headlineContent = { Text(stationKeyword.keyword) },
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
            stationKeyword = StationKeyword(1, "Keyword", 1),
            onClick = {}
        )
    }
}