package org.mothdigital.station_span.data.datasource.local.station

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "station")
data class StationEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo("city") val city: String?,
    @ColumnInfo("country") val country: String?,
    @ColumnInfo("has_announcements") val hasAnnouncements: Boolean?,
    @ColumnInfo("hits") val hits: Int?,
    @ColumnInfo("ibnr") val ibnr: Int?,
    @ColumnInfo("id") val id: Int?,
    @ColumnInfo("is_group") val isGroup: Boolean?,
    @ColumnInfo("is_nearby_station_enabled") val isNearbyStationEnabled: Boolean?,
    @ColumnInfo("latitude") val latitude: Double?,
    @ColumnInfo("localised_name") val localisedName: String?,
    @ColumnInfo("longitude") val longitude: Double?,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("name_slug") val nameSlug: String?,
    @ColumnInfo("region") val region: String?,
)