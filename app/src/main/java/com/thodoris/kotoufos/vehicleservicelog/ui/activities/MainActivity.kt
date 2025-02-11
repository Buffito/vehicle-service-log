package com.thodoris.kotoufos.vehicleservicelog.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.thodoris.kotoufos.vehicleservicelog.R
import com.thodoris.kotoufos.vehicleservicelog.ui.viewmodel.VehicleViewModel


class MainActivity : AppCompatActivity() {
    private val vehicleViewModel: VehicleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        vehicleViewModel.allVehicles.observe(this) { vehicles ->
            Log.d("Main Activity", "Vehicles: $vehicles")
        }
    }
}