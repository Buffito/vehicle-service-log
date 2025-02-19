package com.thodoris.kotoufos.vehicle_service_log.repository

import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import kotlinx.coroutines.flow.Flow

class VehicleRepository(private val vehicleDao: VehicleDao) {
    val allVehicles: Flow<List<Vehicle>> = vehicleDao.getAllVehicles()

    suspend fun insertVehicle(vehicle: Vehicle) {
        vehicleDao.insert(vehicle)
    }

    suspend fun updateVehicle(vehicle: Vehicle) {
        vehicleDao.update(vehicle)
    }

    suspend fun deleteVehicle(vehicle: Vehicle) {
        vehicleDao.delete(vehicle)
    }
}