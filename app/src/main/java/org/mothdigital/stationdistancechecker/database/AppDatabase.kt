package org.mothdigital.stationdistancechecker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time.FetchTimeDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time.FetchTimeEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station.StationDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station.StationEntity
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station_keyword.StationKeywordDao
import org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station_keyword.StationKeywordEntity

@Database(
    entities = [
        FetchTimeEntity::class,
        StationEntity::class,
        StationKeywordEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fetchTimeDao(): FetchTimeDao
    abstract fun stationDao(): StationDao
    abstract fun stationKeywordDao(): StationKeywordDao
}