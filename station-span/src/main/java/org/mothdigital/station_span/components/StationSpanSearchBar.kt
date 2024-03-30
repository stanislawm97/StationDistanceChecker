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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.mothdigital.station_span.domain.model.StationKeyword
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StationSpanSearchBar(
    modifier: Modifier = Modifier,
    stationKeywords: List<StationKeyword>,
    onQueryChange: (String) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        DockedSearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp, bottom = 8.dp),
            query = text,
            onQueryChange = {
                active = it.isNotEmpty()
                onQueryChange(it)
                text = it
            },
            onSearch = { active = false },
            active = active,
            onActiveChange = { },
            placeholder = { Text("Hited search text") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Place,
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
                                active = false
                                text = ""
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
                    ListItem(
                        headlineContent = { Text(item.keyword) },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                            )
                        },
                        modifier = Modifier.clickable {
                            text = item.keyword
                            active = false
                            onItemClick(item.stationId)
                        }
                    )
                }
                if (stationKeywords.isEmpty()) {
                    item {
                        ListItem(
                            headlineContent = { Text("Nie znaleziono żadnej stacji") },
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                )
                            },
                        )
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
            stationKeywords = listOf(
                StationKeyword(0, "TEST", 1)
            ),
            onQueryChange = {},
            onItemClick = {}
        )
    }
}