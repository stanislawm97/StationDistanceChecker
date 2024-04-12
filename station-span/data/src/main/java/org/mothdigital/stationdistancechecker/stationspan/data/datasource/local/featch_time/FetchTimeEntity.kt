package org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fetch_time")
data class FetchTimeEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo("time") val time: Long,
)