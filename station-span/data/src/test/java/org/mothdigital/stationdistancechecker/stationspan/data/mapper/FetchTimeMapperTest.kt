package org.mothdigital.stationdistancechecker.stationspan.data.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time.FetchTimeEntity
import java.util.stream.Stream

class FetchTimeMapperTest {

    companion object {
        @JvmStatic
        fun provideTimes(): Stream<Arguments> = Stream.of(
            Arguments.of(1000L, FetchTimeEntity(time = 1000L)),
            Arguments.of(5000L, FetchTimeEntity(time = 5000L)),
            Arguments.of(-1L, FetchTimeEntity(time = -1L)),
        )
    }

    @ParameterizedTest
    @MethodSource("provideTimes")
    fun `test Long to FetchTimeEntity conversion`(input: Long, expected: FetchTimeEntity) {
        val actual = input.toFetchTimeEntity()
        assertEquals(expected, actual)
    }
}