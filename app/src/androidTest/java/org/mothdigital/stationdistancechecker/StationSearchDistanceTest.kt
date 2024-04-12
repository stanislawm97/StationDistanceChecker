package org.mothdigital.stationdistancechecker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.android.getKoin
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time.FetchTimeDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time.FetchTimeEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station.StationDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station.StationEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station_keyword.StationKeywordDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station_keyword.StationKeywordEntity
import org.mothdigital.stationdistancechecker.utlis.mockStationEntity

@RunWith(AndroidJUnit4::class)
class StationSearchDistanceTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var mockStationEntities: List<StationEntity>
    private lateinit var mockStationKeywordEntities: List<StationKeywordEntity>

    @Before
    fun setupStationsInDatabase() = runTest {
        // Define stations and keywords
        mockStationEntities = listOf(
            mockStationEntity.copy(id = 0, name = "Gdańsk Główny", latitude = 54.35205, longitude = 18.64637),
            mockStationEntity.copy(id = 1, name = "Warszawa Centralna", latitude = 52.22977, longitude = 21.01178)
        )
        mockStationKeywordEntities = listOf(
            StationKeywordEntity(id = 0, stationId = 0, keyword = "Gdańsk Główny"),
            StationKeywordEntity(id = 1, stationId = 1, keyword = "Warszawa Centralna")
        )

        // Insert mock data into the database
        composeTestRule.activity.getKoin().let {
            it.get<FetchTimeDao>().setFetchTime(FetchTimeEntity(0, System.currentTimeMillis()))
            it.get<StationDao>().insertAll(*mockStationEntities.toTypedArray())
            it.get<StationKeywordDao>().insertAll(*mockStationKeywordEntities.toTypedArray())
        }
    }

    @Test
    fun testSearchAndDisplayDistanceBetweenStations() {
        composeTestRule.mainClock.advanceTimeByFrame()

        // Input search queries and select stations
        searchAndSelectStation("firstSearchBar", "Gdańsk", "Gdańsk Główny")
        searchAndSelectStation("secondSearchBar", "War", "Warszawa Centralna")

        // Verify distance display
        composeTestRule
            .onNodeWithTag("distance")
            .onChild()
            .assertIsDisplayed()
            .assertTextEquals("284 km")
    }

    private fun searchAndSelectStation(searchBarTag: String, searchText: String, stationName: String) {
        composeTestRule
            .onNodeWithTag(searchBarTag)
            .onChild()
            .onChildAt(0)
            .performTextInput(searchText)

        composeTestRule
            .onNodeWithText(stationName)
            .performClick()

        composeTestRule
            .onNodeWithTag(searchBarTag)
            .onChild()
            .onChildAt(0)
            .assertTextEquals(stationName)
    }
}