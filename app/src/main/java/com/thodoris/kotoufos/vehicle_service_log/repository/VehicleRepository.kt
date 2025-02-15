package com.thodoris.kotoufos.vehicle_service_log.repository

import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import kotlinx.coroutines.flow.Flow

class VehicleRepository(private val vehicleDao: VehicleDao) {
    val allVehicles: Flow<List<Vehicle>> = vehicleDao.getAllVehicles()

    suspend fun insert(vehicle: Vehicle){
        vehicleDao.insertVehicle(vehicle)
    }

    suspend fun update(vehicle: Vehicle){
        vehicleDao.updateVehicle(vehicle)
    }

    suspend fun delete(vehicle: Vehicle){
        vehicleDao.deleteVehicle(vehicle)
    }
}