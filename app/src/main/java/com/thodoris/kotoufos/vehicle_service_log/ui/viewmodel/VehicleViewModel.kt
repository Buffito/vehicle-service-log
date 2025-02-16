package com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import com.thodoris.kotoufos.vehicle_service_log.data.models.VehicleType
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleRepository
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VehicleViewModel(
    private val vehicleRepository: VehicleRepository,
    private val vehicleTypeRepository: VehicleTypeRepository
) : ViewModel() {
    private val _allVehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val allVehicles: StateFlow<List<Vehicle>> = _allVehicles.asStateFlow()
    val allVehicleTypes: Flow<List<VehicleType>> = vehicleTypeRepository.allVehicleTypes

    init {
        viewModelScope.launch {
            vehicleRepository.allVehicles.collect { vehicles ->
                _allVehicles.value = vehicles
            }

        }
    }

    fun insertVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleRepository.insert(vehicle)
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleRepository.update(vehicle)
        }
    }

    fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleRepository.delete(vehicle)
        }
    }

    fun insertVehicleType(vehicleType: VehicleType) {
        viewModelScope.launch {
            vehicleTypeRepository.insert(vehicleType)
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
