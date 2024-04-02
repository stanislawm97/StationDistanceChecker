package org.mothdigital.station_span.data

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mothdigital.station_span.data.datasource.local.featch_time.FetchTimeDao
import org.mothdigital.station_span.data.datasource.local.featch_time.FetchTimeEntity
import org.mothdigital.station_span.data.datasource.local.station.StationDao
import org.mothdigital.station_span.data.datasource.local.station.StationEntity
import org.mothdigital.station_span.data.datasource.local.station_keyword.StationKeywordDao
import org.mothdigital.station_span.data.datasource.local.station_keyword.StationKeywordEntity
import org.mothdigital.station_span.data.datasource.remote.KoleoApi
import org.mothdigital.station_span.data.datasource.remote.StationDto
import org.mothdigital.station_span.data.datasource.remote.StationKeywordDto
import org.mothdigital.station_span.domain.model.Station
import org.mothdigital.station_span.domain.model.StationKeyword
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class StationSpanRepositoryTest {

    @MockK
    lateinit var koleoApi: KoleoApi

    @MockK
    lateinit var fetchTimeDao: FetchTimeDao

    @MockK
    lateinit var stationDao: StationDao

    @MockK
    lateinit var stationKeywordDao: StationKeywordDao

    @InjectMockKs
    lateinit var repository: StationSpanRepositoryImpl

    @Test
    fun `getStation returns a station`() = runBlocking {
        val stationId = 1
        val mockStation = StationEntity(
            city = "City A",
            country = "Country A",
            hasAnnouncements = true,
            hits = 100,
            ibnr = 1,
            id = 1,
            isGroup = false,
            isNearbyStationEnabled = true,
            latitude = 50.0,
            localisedName = "Local Name A",
            longitude = 10.0,
            name = "Station A",
            nameSlug = "station-a",
            region = "Region A"
        )
        val expected = Station(
            city = "City A",
            country = "Country A",
            hasAnnouncements = true,
            hits = 100,
            ibnr = 1,
            id = 1,
            isGroup = false,
            isNearbyStationEnabled = true,
            latitude = 50.0,
            localisedName = "Local Name A",
            longitude = 10.0,
            name = "Station A",
            nameSlug = "station-a",
            region = "Region A"
        )

        coEvery { stationDao.getById(stationId) } returns mockStation

        val actual = repository.getStation(stationId)

        assertEquals(expected, actual)
        verify(exactly = 1) { stationDao.getById(stationId) }
    }

    @Test
    fun `getStationKeyword returns list of station keywords`() = runBlocking {
        val query = "Station"
        val mockKeywords = listOf(
            StationKeywordEntity(id = 1, keyword = "Station A", stationId = 1),
            StationKeywordEntity(id = 2, keyword = "Station B", stationId = 2)
        )
        val expected = listOf(
            StationKeyword(id = 1, keyword = "Station A", stationId = 1),
            StationKeyword(id = 2, keyword = "Station B", stationId = 2)
        )

        coEvery { stationKeywordDao.findStationsByKeyword("%$query%") } returns mockKeywords

        val actual = repository.getStationKeyword(query)

        assertEquals(expected, actual)
        verify(exactly = 1) { stationKeywordDao.findStationsByKeyword("%$query%") }
    }

    @Test
    fun `getStationKeyword triggers update when data is stale`() = runBlocking {
        val query = "Keyword A"
        val currentTime = System.currentTimeMillis()
        val staleTime = currentTime - 25.hours.inWholeMilliseconds

        coEvery { fetchTimeDao.getFetchTime() } returns FetchTimeEntity(time = staleTime)
        coEvery { koleoApi.fetchStations() } returns listOf(
            StationDto(
                city = "City A",
                country = "Country A",
                hasAnnouncements = true,
                hits = 100,
                ibnr = 1,
                id = 1,
                isGroup = false,
                isNearbyStationEnabled = true,
                latitude = 50.0,
                localisedName = "Local Name A",
                longitude = 10.0,
                name = "Station A",
                nameSlug = "station-a",
                region = "Region A"
            )
        )
        coEvery { koleoApi.fetchStationKeywords() } returns listOf(
            StationKeywordDto(
                id = 1,
                keyword = "Keyword A",
                stationId = 1,
            )
        )
        coEvery { stationDao.insertAll(any()) } just Runs
        coEvery { stationKeywordDao.insertAll(any()) } just Runs
        coEvery { stationKeywordDao.findStationsByKeyword(any()) } returns listOf(
            StationKeywordEntity(
                id = 1,
                keyword = "Keyword A",
                stationId = 1
            )
        )

        repository.getStationKeyword(query)

        coVerify(exactly = 1) { koleoApi.fetchStations() }
        coVerify(exactly = 1) { koleoApi.fetchStationKeywords() }
    }

    @Test
    fun `getStationKeyword handles data fetch failure gracefully`() = runBlocking {
        val query = "Test"
        val currentTime = System.currentTimeMillis()
        val staleTime = currentTime - 25.hours.inWholeMilliseconds
        val exception = RuntimeException("Failed to fetch data")

        coEvery { fetchTimeDao.getFetchTime() } returns FetchTimeEntity(time = staleTime)
        coEvery { koleoApi.fetchStations() } throws exception
        coEvery { koleoApi.fetchStationKeywords() } throws exception
        coEvery { stationKeywordDao.findStationsByKeyword(any()) } returns listOf(
            StationKeywordEntity(
                id = 1,
                keyword = "Keyword A",
                stationId = 1,
            )
        )

        val result = repository.getStationKeyword(query)

        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `getStationKeyword throws CancellationException when data fetch is cancelled`() =
        runBlocking {
            val query = "Test"
            val exception = CancellationException("Fetching data was cancelled")

            coEvery { fetchTimeDao.getFetchTime() } returns FetchTimeEntity(time = System.currentTimeMillis() - 25.hours.inWholeMilliseconds)
            coEvery { koleoApi.fetchStations() } throws exception
            coEvery { koleoApi.fetchStationKeywords() } throws exception

            val job = async { repository.getStationKeyword(query) }

            job.join()

            assertTrue(job.isCancelled)
        }

}
