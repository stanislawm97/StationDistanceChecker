package org.mothdigital.stationdistancechecker.stationspan.data.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station_keyword.StationKeywordEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.remote.StationKeywordDto
import org.mothdigital.stationdistancechecker.stationspan.domain.model.StationKeyword
import java.util.stream.Stream

class StationKeywordMapperTest {

    companion object {
        @JvmStatic
        fun provideDtoToEntity(): Stream<Arguments> = Stream.of(
            Arguments.of(
                StationKeywordDto(id = 1, keyword = "keyword1", stationId = 10),
                StationKeywordEntity(id = 1, keyword = "keyword1", stationId = 10)
            ),
            Arguments.of(
                StationKeywordDto(id = 2, keyword = "keyword2", stationId = 20),
                StationKeywordEntity(id = 2, keyword = "keyword2", stationId = 20)
            )
        )

        @JvmStatic
        fun provideEntityToDomain(): Stream<Arguments> = Stream.of(
            Arguments.of(
                StationKeywordEntity(id = 1, keyword = "keyword1", stationId = 10),
                StationKeyword(id = 1, keyword = "keyword1", stationId = 10)
            ),
            Arguments.of(
                StationKeywordEntity(id = 2, keyword = "keyword2", stationId = 20),
                StationKeyword(id = 2, keyword = "keyword2", stationId = 20)
            )
        )
    }

    @ParameterizedTest
    @MethodSource("provideDtoToEntity")
    fun `test StationKeywordDto to StationKeywordEntity conversion`(
        input: StationKeywordDto,
        expected: StationKeywordEntity
    ) {
        val actual = input.toEntity()
        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("provideEntityToDomain")
    fun `test StationKeywordEntity to StationKeyword domain conversion`(
        input: StationKeywordEntity,
        expected: StationKeyword
    ) {
        val actual = input.toDomain()
        assertEquals(expected, actual)
    }

}