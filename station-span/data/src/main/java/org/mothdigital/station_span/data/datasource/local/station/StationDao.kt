package org.mothdigital.station_span.data.datasource.local.station

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StationDao {

    @Insert
    fun insertAll(vararg users: StationEntity)

    @Query("SELECT * FROM station WHERE id = :id")
    fun getById(id: Int): StationEntity?

    @Query("DELETE FROM station")
    suspend fun deleteAll()
}