package org.mothdigital.stationdistancechecker.stationspan.data.mapper

import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time.FetchTimeEntity

fun Long.toFetchTimeEntity() = FetchTimeEntity(time = this)