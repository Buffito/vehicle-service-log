package com.thodoris.kotoufos.vehicle_service_log.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "service_logs", foreignKeys = [ForeignKey(
        entity = Vehicle::class,
        parentColumns = ["id"],
        childColumns = ["vehicleId"],
        onDelete = ForeignKey.CASCADE // Delete service logs if vehicle is deleted
    )], indices = [Index(value = ["vehicleId"])]
)
data class ServiceLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val vehicleId: Int,
    val date: String,
    val shop: String,
    val mileage: Int,
    val type: String,
    val description: String
)


