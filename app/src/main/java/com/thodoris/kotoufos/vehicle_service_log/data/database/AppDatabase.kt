package com.thodoris.kotoufos.vehicle_service_log.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleDao
import com.thodoris.kotoufos.vehicle_service_log.data.daos.VehicleTypeDao
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import com.thodoris.kotoufos.vehicle_service_log.data.models.VehicleType

@Database(entities = [Vehicle::class, VehicleType::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun vehicleTypeDao(): VehicleTypeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val MIGRATION_1_2 = object : Migration(1, 2) {
                    override fun migrate(db: SupportSQLiteDatabase) {
                        // Example: Adding a new column to the Vehicle table
                        db.execSQL("ALTER TABLE Vehicle ADD COLUMN make TEXT")

                        // Example: Creating a new table for vehicle types
                        db.execSQL(
                            "CREATE TABLE IF NOT EXISTS VehicleType (" + "name TEXT  PRIMARY KEY NOT NULL)"
                        )
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
    }


}
