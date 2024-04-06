package org.mothdigital.station_span.data.datasource.local.station

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.mothdigital.station_span.data.datasource.local.station_keyword.StationKeywordEntity

@Dao
interface StationDao {

    @Insert
    fun insertAll(vararg users: StationEntity)

    @Query("SELECT * FROM station WHERE id = :id")
    fun getById(id: Int): StationEntity?

    @Query(
        """
        SELECT DISTINCT s.* 
        FROM station s
        JOIN station_keyword sk ON sk.station_id = s.id
        WHERE sk.keyword LIKE :searchQuery
        ORDER BY s.hits DESC
        LIMIT 5
        """
    )
    fun findStationsByKeyword(searchQuery: String): List<StationEntity>

    @Query("DELETE FROM station")
    suspend fun deleteAll()
}