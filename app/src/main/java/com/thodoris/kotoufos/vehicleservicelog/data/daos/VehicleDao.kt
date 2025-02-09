package com.thodoris.kotoufos.vehicleservicelog.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.thodoris.kotoufos.vehicleservicelog.data.models.Vehicle

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles")
    fun getAllVehicles(): LiveData<List<Vehicle>>

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Insert
    suspend fun insertVehicle(vehicle: Vehicle)

    @Delete
    suspend fun deleteVehicle(vehicle: Vehicle)
}