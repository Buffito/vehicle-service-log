package com.thodoris.kotoufos.vehicleservicelog.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shops")
data class Shop(
    @PrimaryKey val id: Int,

    val description: String,
    val address: String,
    val phone: String
)