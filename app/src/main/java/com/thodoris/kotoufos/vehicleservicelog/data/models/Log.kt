package com.thodoris.kotoufos.vehicleservicelog.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Log(
    @PrimaryKey val id: Int,

    val mileage: Float,
    val date: Date,
    val type: String,
    val shop: String,
    val description: String,
    val vehicle: Vehicle
)