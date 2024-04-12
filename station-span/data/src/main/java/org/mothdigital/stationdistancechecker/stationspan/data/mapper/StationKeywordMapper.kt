package org.mothdigital.stationdistancechecker.stationspan.data.mapper

import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station_keyword.StationKeywordEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.remote.StationKeywordDto
import org.mothdigital.stationdistancechecker.stationspan.domain.model.StationKeyword

fun StationKeywordDto.toEntity() = StationKeywordEntity(
    id = id,
    keyword = keyword,
    stationId = stationId,
)

fun StationKeywordEntity.toDomain() = StationKeyword(
    id = id,
    keyword = keyword,
    stationId = stationId,
)