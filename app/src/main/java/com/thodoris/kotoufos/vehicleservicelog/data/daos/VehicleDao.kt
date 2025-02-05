package com.thodoris.kotoufos.vehicleservicelog.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thodoris.kotoufos.vehicleservicelog.data.models.Vehicle

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles")
    fun get_vehicles():
            LiveData<List<Vehicle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: Vehicle)
}