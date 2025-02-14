package com.thodoris.kotoufos.vehicle_service_log.repository

import androidx.lifecycle.LiveData
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle

class VehicleRepository(private val vehicleDao: VehicleDao) {
    val allVehicles: LiveData<List<Vehicle>> = vehicleDao.getAllVehicles()

    suspend fun insert(vehicle: Vehicle){
        vehicleDao.insertVehicle(vehicle)
    }
}