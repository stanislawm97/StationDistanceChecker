package org.mothdigital.station_span.data.mapper

import org.mothdigital.station_span.data.datasource.local.station.StationEntity
import org.mothdigital.station_span.data.datasource.remote.StationDto
import org.mothdigital.station_span.domain.model.Station

fun StationDto.toEntity() = StationEntity(
    city = city,
    country = country,
    hasAnnouncements = hasAnnouncements,
    hits = hits,
    ibnr = ibnr,
    id = id,
    isGroup = isGroup,
    isNearbyStationEnabled = isNearbyStationEnabled,
    latitude = latitude,
    localisedName = localisedName,
    longitude = longitude,
    name = name,
    nameSlug = nameSlug,
    region = region,
)

fun StationEntity.toDomain() = Station(
    city = city,
    country = country,
    hasAnnouncements = hasAnnouncements,
    hits = hits,
    ibnr = ibnr,
    id = id,
    isGroup = isGroup,
    isNearbyStationEnabled = isNearbyStationEnabled,
    latitude = latitude,
    localisedName = localisedName,
    longitude = longitude,
    name = name,
    nameSlug = nameSlug,
    region = region,
)