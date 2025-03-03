package com.thodoris.kotoufos.vehicle_service_log.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thodoris.kotoufos.vehicle_service_log.data.database.AppDatabase
import com.thodoris.kotoufos.vehicle_service_log.repository.ServiceLogRepository
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleRepository
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleTypeRepository
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs.InsertUpdateServiceScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs.ServiceInfoScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs.ServiceLogScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles.InsertUpdateVehicleScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles.VehicleInfoScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles.VehiclesScreen
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModel
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModelFactory
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModelFactory

@Composable
fun AppNavHost(database: AppDatabase) {

    val vehicleDao = database.vehicleDao()
    val vehicleTypeDao = database.vehicleTypeDao()

    val vehicleRepository = VehicleRepository(vehicleDao)
    val vehicleTypeRepository = VehicleTypeRepository(vehicleTypeDao)

    val vehicleViewModel: VehicleViewModel = viewModel(
        factory = VehicleViewModelFactory(vehicleRepository, vehicleTypeRepository)
    )

    val serviceLogDao = database.serviceLogDao()
    val serviceLogRepository = ServiceLogRepository(serviceLogDao)
    val serviceLogViewModel: ServiceLogViewModel = viewModel(
        factory = ServiceLogViewModelFactory(serviceLogRepository)
    )

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

