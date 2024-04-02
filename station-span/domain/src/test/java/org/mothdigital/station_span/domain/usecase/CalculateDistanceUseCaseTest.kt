package org.mothdigital.station_span.domain.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CalculateDistanceUseCaseTest {
    companion object {
        @JvmStatic
        fun distanceData(): Stream<Arguments> = Stream.of(
            Arguments.of(52.2296756, 21.0122287, 52.406374, 16.9251681, 278_458),
            Arguments.of(52.2296756, 21.0122287, 52.406374, null, 0),
            Arguments.of(52.2296756, 21.0122287, null, 16.9251681, 0),
            Arguments.of(52.2296756, null, 52.406374, 16.9251681, 0),
            Arguments.of(null, 21.0122287, 52.406374, 16.9251681, 0),
            Arguments.of(null, null, null, null, 0)
        )
    }

    @ParameterizedTest
    @MethodSource("distanceData")
    fun `calculate distance correctly`(
        lat1: Double?,
        lon1: Double?,
        lat2: Double?,
        lon2: Double?,
        expectedDistanceInMeters: Long
    ) {
        val useCase = CalculateDistanceUseCase()
        val result = useCase(lat1, lon1, lat2, lon2)

        assertEquals(expectedDistanceInMeters, result.value)
    }
}