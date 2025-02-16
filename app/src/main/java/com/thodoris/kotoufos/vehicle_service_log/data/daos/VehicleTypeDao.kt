package com.thodoris.kotoufos.vehicle_service_log.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thodoris.kotoufos.vehicle_service_log.data.models.VehicleType
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleTypeDao {
    @Query("SELECT * FROM vehicle_types")
    fun getAllTypes(): Flow<List<VehicleType>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(type: VehicleType)

}
