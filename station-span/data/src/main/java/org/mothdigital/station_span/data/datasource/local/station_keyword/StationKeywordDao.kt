package org.mothdigital.station_span.data.datasource.local.station_keyword

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StationKeywordDao {

    @Insert
    fun insertAll(vararg stations: StationKeywordEntity)

    @Query("DELETE FROM station_keyword")
    suspend fun deleteAll()
}