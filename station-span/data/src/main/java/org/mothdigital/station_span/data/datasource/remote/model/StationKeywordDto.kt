package org.mothdigital.station_span.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class StationKeywordDto(
    @SerializedName("id") val id: Int,
    @SerializedName("keyword") val keyword: String,
    @SerializedName("station_id") val stationId: Int
)