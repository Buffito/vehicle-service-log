package com.thodoris.kotoufos.vehicleservicelog.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey val id: Int,

    val model: String,
    val licencePlate: String,
    val type: String,
    val active: Boolean
)