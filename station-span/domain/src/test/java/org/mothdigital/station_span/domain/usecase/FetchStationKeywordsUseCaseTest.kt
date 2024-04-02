package org.mothdigital.station_span.domain.usecase

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
import org.mothdigital.station_span.domain.StationSpanRepository
import org.mothdigital.station_span.domain.model.StationKeyword

@ExtendWith(MockKExtension::class)
class FetchStationKeywordsUseCaseTest {

    @MockK
    lateinit var stationSpanRepository: StationSpanRepository

    @InjectMockKs
    lateinit var fetchStationKeywordsUseCase: FetchStationKeywordsUseCase

    @Test
    fun `invoke returns list of StationKeyword when repository succeeds`() = runBlocking {
        // Given
        val query = "test"
        val expectedKeywords = listOf(StationKeyword(id = 1, keyword = "Test Station", stationId = 123))
        coEvery { stationSpanRepository.getStationKeyword(query) } returns expectedKeywords

        // When
        val result = fetchStationKeywordsUseCase(query)

        // Then
        assertEquals(expectedKeywords, result)
        coVerify(exactly = 1) { stationSpanRepository.getStationKeyword(query) }
    }

    @Test
    fun `invoke returns empty list when repository fails`() = runBlocking {
        // Given
        val query = "test"
        coEvery { stationSpanRepository.getStationKeyword(query) } throws Exception()

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

        coEvery { stationSpanRepository.getStationKeyword(query) } throws cancellationException

        // Then
        val job = async { fetchStationKeywordsUseCase(query) }

        job.join()

        assertTrue(job.isCancelled)
    }
}