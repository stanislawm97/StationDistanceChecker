package org.mothdigital.station_span.data.mapper

import org.mothdigital.station_span.data.datasource.local.station_keyword.StationKeywordEntity
import org.mothdigital.station_span.data.datasource.remote.StationKeywordDto
import org.mothdigital.station_span.domain.model.StationKeyword

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