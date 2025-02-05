package com.thodoris.kotoufos.vehicleservicelog.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thodoris.kotoufos.vehicleservicelog.data.models.Log

@Dao
interface LogDao {
    @Query("SELECT * FROM logs")
    fun get_logs():
            LiveData<List<Log>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: Log)
}