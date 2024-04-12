package org.mothdigital.stationdistancechecker.stationspan.data.mapper

import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station.StationEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.remote.StationDto
import org.mothdigital.stationdistancechecker.stationspan.domain.model.Station

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