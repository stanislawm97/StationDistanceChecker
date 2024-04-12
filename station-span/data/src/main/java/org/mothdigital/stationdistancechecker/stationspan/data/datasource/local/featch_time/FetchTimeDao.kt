package org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.featch_time

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FetchTimeDao {

    @Query("SELECT * FROM fetch_time ORDER BY time DESC LIMIT 1")
    fun getFetchTime(): FetchTimeEntity?

    @Insert
    fun setFetchTime(fetchTimeEntity: FetchTimeEntity)
}