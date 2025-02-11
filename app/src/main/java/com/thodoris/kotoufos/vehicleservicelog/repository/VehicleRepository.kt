package com.thodoris.kotoufos.vehicleservicelog.repository

import androidx.lifecycle.LiveData
import com.thodoris.kotoufos.vehicleservicelog.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicleservicelog.data.models.Vehicle

class VehicleRepository(private val vehicleDao: VehicleDao) {
    val allVehicles: LiveData<List<Vehicle>> = vehicleDao.getAllVehicles()

    suspend fun insert(vehicle: Vehicle){
        vehicleDao.insertVehicle(vehicle)
    }
}