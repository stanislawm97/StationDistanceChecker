package org.mothdigital.stationdistancechecker.stationspan.data.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station.StationEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station_keyword.StationKeywordEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.remote.StationDto
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.remote.StationKeywordDto
import java.util.stream.Stream

class StationMapperTest {

    companion object {
        @JvmStatic
        fun stationDtoToEntityData(): Stream<Arguments> = Stream.of(
            Arguments.of(
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
                ),
                StationEntity(
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
        )

        @JvmStatic
        fun stationKeywordDtoToEntityData(): Stream<Arguments> = Stream.of(
            Arguments.of(
                StationKeywordDto(id = 1, keyword = "Keyword A", stationId = 1),
                StationKeywordEntity(id = 1, keyword = "Keyword A", stationId = 1)
            )
        )
    }

    @ParameterizedTest
    @MethodSource("stationDtoToEntityData")
    fun `test StationDto to StationEntity conversion`(input: StationDto, expected: StationEntity) {
        val actual = input.toEntity()
        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("stationKeywordDtoToEntityData")
    fun `test StationKeywordDto to StationKeywordEntity conversion`(
        input: StationKeywordDto,
        expected: StationKeywordEntity
    ) {
        val actual = input.toEntity()
        assertEquals(expected, actual)
    }
}