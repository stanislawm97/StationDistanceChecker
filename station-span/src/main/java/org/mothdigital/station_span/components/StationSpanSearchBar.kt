package org.mothdigital.station_span.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.mothdigital.station_span.domain.model.StationKeyword
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme
import org.mothdigital.stationdistancechecker.station_span.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StationSpanSearchBar(
    modifier: Modifier = Modifier,
    hint: String,
    iconTint: Color,
    stationKeywords: List<StationKeyword>,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onItemClick: (Int) -> Unit,
    onClear: () -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }

    Box(
        modifier
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        DockedSearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(12.dp),
            query = text,
            onQueryChange = {
                onQueryChange(it)
                text = it
            },
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            placeholder = { Text(hint) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Place,
                    tint = iconTint,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = text.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Icon(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .clickable {
                                text = ""
                                onClear()
                            },
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(stationKeywords) { item ->
                    StationListItem(
                        stationKeyword = item,
                        onClick = {
                            text = item.keyword
                            onItemClick(item.stationId)
                        }
                    )
                }
                if (stationKeywords.isEmpty()) {
                    item {
                        NoStationsFoundListItem()
                    }
                }
            }
        }
    }
}

@Preview(name = "StationSpanSearchBar")
@Composable
private fun PreviewStationSpanSearchBar() {
    StationDistanceCheckerTheme {
        StationSpanSearchBar(
            modifier = Modifier,
            hint = "Example hint",
            iconTint = MaterialTheme.colorScheme.primary,
            stationKeywords = listOf(
                StationKeyword(0, "TEST 1", 1),
                StationKeyword(1, "TEST 2", 2),
                StationKeyword(3, "TEST 3", 3),
                StationKeyword(4, "TEST 4", 4),
                StationKeyword(5, "TEST 5", 5),
            ),
            active = true,
            onActiveChange = {},
            onQueryChange = {},
            onSearch = {},
            onItemClick = {},
            onClear = {},
        )
    }
}