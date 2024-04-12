package org.mothdigital.stationdistancechecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.mothdigital.stationdistancechecker.stationspan.StationSpanRoute
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StationDistanceCheckerTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        StationSpanRoute()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StationDistanceCheckerTheme {
        Content()
    }
}