package org.mothdigital.stationdistancechecker.stationspan.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.mothdigital.stationdistancechecker.stationspan.R

@Composable
fun MeasureDistanceTitle(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.title_measure_distance),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(name = "MeasureDistanceTitle")
@Composable
private fun PreviewMeasureDistanceTitle() {

    MeasureDistanceTitle()
}