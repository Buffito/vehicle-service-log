package com.thodoris.kotoufos.vehicleservicelog.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thodoris.kotoufos.vehicleservicelog.data.models.Shop

@Dao
interface ShopDao {
    @Query("SELECT * FROM shops")
    fun get_shops():
            LiveData<List<Shop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shop: Shop)
}