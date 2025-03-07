package com.thodoris.kotoufos.vehicle_service_log.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thodoris.kotoufos.vehicle_service_log.data.database.AppDatabase
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs.InsertUpdateServiceScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs.ServiceInfoScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs.ServiceLogScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles.InsertUpdateVehicleScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles.VehicleInfoScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles.VehiclesScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModel
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel

@Composable
fun AppNavHost(database: AppDatabase) {
    val vehicleViewModel: VehicleViewModel = hiltViewModel()
    val serviceLogViewModel: ServiceLogViewModel = hiltViewModel()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { VehiclesScreen(vehicleViewModel, navController) }
        composable("vehicle/{vehicleId}") { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toIntOrNull()
            if (vehicleId != null) {
                InsertUpdateVehicleScreen(vehicleId, vehicleViewModel, navController)
            }
        }
        composable("vehicle_info/{vehicleId}") { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toIntOrNull()
            if (vehicleId != null) {
                VehicleInfoScreen(vehicleId, vehicleViewModel, navController)
            }
        }
        composable("service_log/{vehicleId}") { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toIntOrNull()
            if (vehicleId != null) {
                ServiceLogScreen(vehicleId, serviceLogViewModel, vehicleViewModel, navController)
            }
        }
        composable("service/{serviceId}/{vehicleId}") { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId")?.toIntOrNull()
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toIntOrNull()

            if ((vehicleId != null) && (serviceId != null)) {
                InsertUpdateServiceScreen(
                    vehicleId, serviceId, serviceLogViewModel, vehicleViewModel, navController
                )
            }
        }
        composable("service_info/{logId}") { backStackEntry ->
            val logId = backStackEntry.arguments?.getString("logId")?.toIntOrNull()
            if (logId != null) {
                ServiceInfoScreen(logId, serviceLogViewModel, navController)
            }
        }
    }
}

