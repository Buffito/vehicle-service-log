package com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import com.thodoris.kotoufos.vehicle_service_log.data.models.VehicleType
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleRepository
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class VehicleViewModel(
    private val vehicleRepository: VehicleRepository,
    private val vehicleTypeRepository: VehicleTypeRepository
) : ViewModel() {
    val allVehicles: Flow<List<Vehicle>> = vehicleRepository.allVehicles
    val allVehicleTypes: Flow<List<VehicleType>> = vehicleTypeRepository.allVehicleTypes

    fun vehicleById(vehicleId: Int): Flow<Vehicle> {
        return vehicleRepository.getVehicleById(vehicleId)
    }

    fun insertVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleRepository.insertVehicle(vehicle)
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleRepository.updateVehicle(vehicle)
        }
    }

    fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleRepository.deleteVehicle(vehicle)
        }
    }

    fun insertVehicleType(vehicleType: VehicleType) {
        viewModelScope.launch {
            vehicleTypeRepository.insertVehicleType(vehicleType)
        }
    }

    fun updateVehicleType(vehicleType: VehicleType) {
        viewModelScope.launch {
            vehicleTypeRepository.updateVehicleType(vehicleType)
        }
    }

    fun deleteVehicleType(vehicleType: VehicleType) {
        viewModelScope.launch {
            vehicleTypeRepository.deleteVehicleType(vehicleType)
        }
    }
}

class VehicleViewModelFactory(
    private val vehicleRepository: VehicleRepository,
    private val vehicleTypeRepository: VehicleTypeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleViewModel::class.java)) {
            return VehicleViewModel(vehicleRepository, vehicleTypeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
