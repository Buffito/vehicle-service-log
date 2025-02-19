package com.thodoris.kotoufos.vehicle_service_log.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thodoris.kotoufos.vehicle_service_log.data.daos.ServiceLogDao
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleTypeDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.ServiceLog
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import com.thodoris.kotoufos.vehicle_service_log.data.models.VehicleType

@Database(entities = [Vehicle::class, VehicleType::class, ServiceLog::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun vehicleTypeDao(): VehicleTypeDao
    abstract fun serviceLogDao(): ServiceLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }


}