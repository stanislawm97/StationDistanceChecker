package org.mothdigital.stationdistancechecker.stationspan.data.datasource.local.station

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

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