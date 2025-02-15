package com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VehicleViewModel(private val repository: VehicleRepository) : ViewModel() {
    private val _allVehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val allVehicles: StateFlow<List<Vehicle>> = _allVehicles.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allVehicles.collect { vehicles ->
                _allVehicles.value = vehicles
            }
        }
    }

    fun insert(vehicle: Vehicle){
        viewModelScope.launch {
            repository.insert(vehicle)
        }
    }

    fun update(vehicle: Vehicle){
        viewModelScope.launch {
            repository.update(vehicle)
        }
    }

    fun delete(vehicle: Vehicle){
        viewModelScope.launch {
            repository.delete(vehicle)
        }
    }
}

class VehicleViewModelFactory(private val vehicleDao: VehicleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = VehicleRepository(vehicleDao)
        return VehicleViewModel(repository) as T
    }
}