package com.thodoris.kotoufos.vehicle_service_log.di

import android.content.Context
import androidx.room.Room
import com.thodoris.kotoufos.vehicle_service_log.data.daos.ServiceLogDao
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleTypeDao
import com.thodoris.kotoufos.vehicle_service_log.data.database.AppDatabase
import com.thodoris.kotoufos.vehicle_service_log.repository.ServiceLogRepository
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleRepository
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleTypeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideVehicleRepository(
        vehicleDao: VehicleDao
    ): VehicleRepository {
        return VehicleRepository(vehicleDao)
    }

    @Provides
    fun provideVehicleTypeRepository(
        vehicleTypeDao: VehicleTypeDao
    ): VehicleTypeRepository {
        return VehicleTypeRepository(vehicleTypeDao)
    }

    @Provides
    fun provideServiceLogRepository(
        serviceLogDao: ServiceLogDao
    ): ServiceLogRepository {
        return ServiceLogRepository(serviceLogDao)
    }

    @Provides
    fun provideVehicleDao(db: AppDatabase): VehicleDao = db.vehicleDao()

    @Provides
    fun provideVehicleTypeDao(db: AppDatabase): VehicleTypeDao = db.vehicleTypeDao()

    @Provides
    fun provideServiceLogDao(db: AppDatabase): ServiceLogDao = db.serviceLogDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
}