package com.thodoris.kotoufos.vehicle_service_log.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_types")
data class VehicleType(
    @PrimaryKey val name: String
)
