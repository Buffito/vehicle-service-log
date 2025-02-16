package com.thodoris.kotoufos.vehicle_service_log.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val make: String,
    val model: String,
    val licencePlate: String,
    val type: String,
    val active: Boolean
)