package com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thodoris.kotoufos.vehicle_service_log.data.models.ServiceLog
import com.thodoris.kotoufos.vehicle_service_log.repository.ServiceLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceLogViewModel @Inject constructor(private val serviceLogRepository: ServiceLogRepository) :
    ViewModel() {
    fun allServiceLogsForVehicle(vehicleId: Int): Flow<List<ServiceLog>> =
        serviceLogRepository.allVehicleServiceLogs(vehicleId)

    fun logById(logId: Int): Flow<ServiceLog> {
        return serviceLogRepository.getServiceById(logId)
    }

    fun insertServiceLog(serviceLog: ServiceLog) {
        viewModelScope.launch {
            serviceLogRepository.insertServiceLog(serviceLog)
        }
    }

    fun deleteServiceLog(serviceLog: ServiceLog) {
        viewModelScope.launch {
            serviceLogRepository.deleteServiceLog(serviceLog)
        }
    }

    fun updateServiceLog(serviceLog: ServiceLog) {
        viewModelScope.launch {
            serviceLogRepository.updateServiceLog(serviceLog)
        }
    }
}
