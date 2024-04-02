package org.mothdigital.stationdistancechecker

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mothdigital.stationdistancechecker.design.theme.StationDistanceCheckerTheme

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun useAppContext() = runTest {
        composeTestRule.setContent {
            StationDistanceCheckerTheme {
                Content()
            }
        }

        delay(15_000)
    }
}