package org.mothdigital.stationdistancechecker.stationspan.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mothdigital.stationdistancechecker.stationspan.domain.StationSpanRepository
import org.mothdigital.stationdistancechecker.stationspan.domain.model.Station

@ExtendWith(MockKExtension::class)
class FetchStationKeywordsUseCaseTest {

    @MockK
    lateinit var stationSpanRepository: StationSpanRepository

    @InjectMockKs
    lateinit var fetchStationKeywordsUseCase: FetchStationKeywordsUseCase

    @Test
    fun `invoke returns list of Station when repository succeeds`() = runBlocking {
        // Given
        val query = "Station"
        val expectedKeywords = listOf(
            Station(
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
        coEvery { stationSpanRepository.getStationByKeyword(query) } returns expectedKeywords

        // When
        val result = fetchStationKeywordsUseCase(query)

        // Then
        assertEquals(expectedKeywords, result)
        coVerify(exactly = 1) { stationSpanRepository.getStationByKeyword(query) }
    }

    @Test
    fun `invoke returns empty list when repository fails`() = runBlocking {
        // Given
        val query = "test"
        coEvery { stationSpanRepository.getStationByKeyword(query) } throws Exception()

        // When
        val result = fetchStationKeywordsUseCase(query)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke rethrows CancellationException when repository throws CancellationException`() = runBlocking {
        // Given
        val query = "test"
        val cancellationException = kotlinx.coroutines.CancellationException("Cancelled")

        coEvery { stationSpanRepository.getStationByKeyword(query) } throws cancellationException

        // Then
        val job = async { fetchStationKeywordsUseCase(query) }

        job.join()

        assertTrue(job.isCancelled)
    }
}