package org.mothdigital.station_span.data.mapper

import org.mothdigital.station_span.data.datasource.local.featch_time.FetchTimeEntity

fun Long.toFetchTimeEntity() = FetchTimeEntity(time = this)