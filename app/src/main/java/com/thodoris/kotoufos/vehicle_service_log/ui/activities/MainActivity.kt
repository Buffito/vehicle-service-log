package com.thodoris.kotoufos.vehicle_service_log.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.thodoris.kotoufos.vehicle_service_log.data.database.AppDatabase
import com.thodoris.kotoufos.vehicle_service_log.navigation.AppNavHost
import com.thodoris.kotoufos.vehicle_service_log.ui.theme.VehicleservicelogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDatabase.getDatabase(this)
        enableEdgeToEdge()
        setContent {
            VehicleservicelogTheme {
                AppNavHost()
            }
        }
    }
}