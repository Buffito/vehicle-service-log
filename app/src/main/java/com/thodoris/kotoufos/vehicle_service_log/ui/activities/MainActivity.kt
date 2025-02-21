package com.thodoris.kotoufos.vehicle_service_log.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thodoris.kotoufos.vehicle_service_log.data.database.AppDatabase
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import com.thodoris.kotoufos.vehicle_service_log.data.models.VehicleType
import com.thodoris.kotoufos.vehicle_service_log.repository.ServiceLogRepository
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleRepository
import com.thodoris.kotoufos.vehicle_service_log.repository.VehicleTypeRepository
import com.thodoris.kotoufos.vehicle_service_log.ui.theme.VehicleservicelogTheme
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModel
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModelFactory
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(this)
        enableEdgeToEdge()
        setContent {
            VehicleservicelogTheme {
                AppNavHost(database)
            }
        }
    }
}


@Composable
fun VehicleFormScreen(viewModel: VehicleViewModel, navController: NavHostController) {
    val vehicles by viewModel.allVehicles.collectAsState(emptyList())

    // Not how it should be done, but good for now
    viewModel.insertVehicleType(VehicleType(name = "Car"))
    viewModel.insertVehicleType(VehicleType(name = "Motorcycle"))
    viewModel.insertVehicleType(VehicleType(name = "Truck"))

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Vehicle List", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            LazyColumn(
                modifier = Modifier.weight(1f, fill = false)
            ) {
                items(vehicles) { vehicle ->
                    VehicleItem(vehicle = vehicle, onClick = {
                        navController.navigate("service_log/${vehicle.id}")
                    })
                }
            }

            Button(
                onClick = { navController.navigate("add_vehicle") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text("Add Vehicle")

            }
        }
    })
}

@Composable
fun AddVehicleScreen(viewModel: VehicleViewModel, navController: NavHostController) {
    val vehicleTypes by viewModel.allVehicleTypes.collectAsState(emptyList())

    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var licencePlate by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Add New Vehicle", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            OutlinedTextField(value = make,
                onValueChange = { make = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Model") })

            OutlinedTextField(value = model,
                onValueChange = { model = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Model") })

            OutlinedTextField(value = licencePlate,
                onValueChange = { licencePlate = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Licence Plate") })

            Box {
                OutlinedTextField(value = type,
                    onValueChange = { },
                    label = { Text("Type") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    })

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.zIndex(1f)) {
                    vehicleTypes.forEach { option ->
                        DropdownMenuItem(text = { Text(option.name) }, onClick = {
                            type = option.name
                            expanded = false
                        })
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { active = !active }) {
                Checkbox(checked = active, onCheckedChange = { active = it })
                Text(text = "Active")
            }

            Button(
                onClick = {
                    if (make.isNotEmpty() && model.isNotEmpty() && licencePlate.isNotEmpty() && type.isNotEmpty()) {
                        val newVehicle = Vehicle(
                            make = make,
                            model = model,
                            licencePlate = licencePlate,
                            type = type,
                            active = active
                        )
                        viewModel.insertVehicle(newVehicle)
                    }

                    navController.navigate("main")
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Vehicle")
            }
        }
    })
}

@Composable
fun ServiceLogScreen(vehicleId: Int, viewModel: ServiceLogViewModel, navController: NavHostController) {
    val serviceLogs by viewModel.allServiceLogsForVehicle(vehicleId).collectAsState(emptyList())

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Service Log", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            LazyColumn(
                modifier = Modifier.weight(1f, fill = false)
            ) {
                items(serviceLogs) { log ->
                    //TODO: Add log stuff
                }
            }

            Button(
                onClick = { navController.navigate("add_service${vehicleId}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text("Add Service")

            }
        }
    })
}

@Composable
fun AddServiceScreen(vehicleId: Int, viewModel: ServiceLogViewModel, navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Add Service", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        }
    })
}

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
        composable("main") { VehicleFormScreen(vehicleViewModel, navController) }
        composable("add_vehicle") { AddVehicleScreen(vehicleViewModel, navController) }
        composable("service_log/{vehicleId}") { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toIntOrNull()
            if (vehicleId != null) {
                ServiceLogScreen(vehicleId, serviceLogViewModel, navController)
            }
        }
        composable("add_service/{vehicleId}") { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toIntOrNull()
            if (vehicleId != null) {
                AddServiceScreen(vehicleId, serviceLogViewModel, navController)
            }
        }
    }
}

@Composable
fun VehicleItem(vehicle: Vehicle, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }, // Handle click event
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${vehicle.make} ${vehicle.model}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Licence Plate: ${vehicle.licencePlate}", fontSize = 14.sp)
            Text(text = "Type: ${vehicle.type}", fontSize = 14.sp)
            Text(text = "Active: ${if (vehicle.active) "Yes" else "No"}", fontSize = 14.sp)
        }
    }
}