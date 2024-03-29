package org.mothdigital.station_span.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class StationDto(
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String,
    @SerializedName("has_announcements") val hasAnnouncements: Boolean,
    @SerializedName("hits") val hits: Int,
    @SerializedName("ibnr") val ibnr: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("is_group") val isGroup: Boolean,
    @SerializedName("is_nearby_station_enabled") val isNearbyStationEnabled: Boolean,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("localised_name") val localisedName: String,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("name") val name: String,
    @SerializedName("name_slug") val nameSlug: String,
    @SerializedName("region") val region: String
)