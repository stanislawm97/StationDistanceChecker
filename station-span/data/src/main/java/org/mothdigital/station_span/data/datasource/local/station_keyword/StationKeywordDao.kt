package org.mothdigital.station_span.data.datasource.local.station_keyword

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StationKeywordDao {

    @Insert
    fun insertAll(vararg stations: StationKeywordEntity)

    @Query(
        """
        SELECT sk.* 
        FROM station_keyword sk
        JOIN station s ON sk.station_id = s.id
        WHERE sk.keyword LIKE :searchQuery
        ORDER BY s.hits DESC
        LIMIT 10
        """
    )
    fun findStationsByKeyword(searchQuery: String): List<StationKeywordEntity>

    @Query("DELETE FROM station_keyword")
    suspend fun deleteAll()
}