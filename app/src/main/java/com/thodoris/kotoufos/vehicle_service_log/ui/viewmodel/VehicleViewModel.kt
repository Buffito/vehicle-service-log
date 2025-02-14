package com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import kotlinx.coroutines.launch

class VehicleViewModel(private val vehicleDao: VehicleDao) : ViewModel() {
    val allVehicles : LiveData<List<Vehicle>> = vehicleDao.getAllVehicles()

    fun insert(vehicle: Vehicle){
        viewModelScope.launch {
            vehicleDao.insertVehicle(vehicle)
        }
    }

    fun update(vehicle: Vehicle){
        viewModelScope.launch {
            vehicleDao.updateVehicle(vehicle)
        }
    }

    fun delete(vehicle: Vehicle){
        viewModelScope.launch {
            vehicleDao.deleteVehicle(vehicle)
        }
    }
}