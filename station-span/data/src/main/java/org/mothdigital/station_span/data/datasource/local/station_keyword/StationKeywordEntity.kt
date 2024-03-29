package org.mothdigital.station_span.data.datasource.local.station_keyword

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "station_keyword")
data class StationKeywordEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("keyword") val keyword: String,
    @ColumnInfo("station_id") val stationId: Int
)