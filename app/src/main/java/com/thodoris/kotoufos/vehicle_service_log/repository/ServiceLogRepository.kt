package com.thodoris.kotoufos.vehicle_service_log.repository

import com.thodoris.kotoufos.vehicle_service_log.data.daos.ServiceLogDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.ServiceLog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceLogRepository @Inject constructor(private val serviceLogDao: ServiceLogDao) {
    fun allVehicleServiceLogs(vehicleId: Int): Flow<List<ServiceLog>> =
        serviceLogDao.getAllServiceLogsForVehicle(vehicleId)

    fun getServiceById(logId: Int): Flow<ServiceLog> {
        return serviceLogDao.getLogById(logId)
    }

    suspend fun insertServiceLog(serviceLog: ServiceLog) {
        serviceLogDao.insert(serviceLog)
    }

    suspend fun updateServiceLog(serviceLog: ServiceLog) {
        serviceLogDao.update(serviceLog)
    }

    suspend fun deleteServiceLog(serviceLog: ServiceLog) {
        serviceLogDao.delete(serviceLog)
    }
}