package com.thodoris.kotoufos.vehicle_service_log.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.thodoris.kotoufos.vehicle_service_log.data.models.ServiceLog
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceLogDao {
    @Query("SELECT * FROM service_logs WHERE vehicleId = :vehicleId")
    fun getAllServiceLogsForVehicle(vehicleId: Int): Flow<List<ServiceLog>>

    @Query("SELECT * FROM service_logs WHERE id = :logId LIMIT 1")
    fun getLogById(logId: Int): Flow<ServiceLog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceLog: ServiceLog)

    @Update
    suspend fun update(serviceLog: ServiceLog)

    @Delete
    suspend fun delete(serviceLog: ServiceLog)
}