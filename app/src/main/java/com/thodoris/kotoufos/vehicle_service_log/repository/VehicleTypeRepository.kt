package com.thodoris.kotoufos.vehicle_service_log.repository

import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleTypeDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.VehicleType
import kotlinx.coroutines.flow.Flow

class VehicleTypeRepository(private val vehicleTypeDao: VehicleTypeDao) {
    val allVehicleTypes: Flow<List<VehicleType>> = vehicleTypeDao.getAllTypes()

    suspend fun getVehicleTypeCount(): Int {
        return vehicleTypeDao.getCount()
    }

    suspend fun insertVehicleType(vehicleType: VehicleType) {
        vehicleTypeDao.insert(vehicleType)
    }

    suspend fun updateVehicleType(vehicleType: VehicleType) {
        vehicleTypeDao.update(vehicleType)
    }

    suspend fun deleteVehicleType(vehicleType: VehicleType) {
        vehicleTypeDao.delete(vehicleType)
    }
}