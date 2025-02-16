package com.thodoris.kotoufos.vehicle_service_log.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thodoris.kotoufos.vehicle_service_log.data.database.AppDatabase
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import com.thodoris.kotoufos.vehicle_service_log.ui.theme.VehicleservicelogTheme
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val vehicleDao = database.vehicleDao()
        val viewModel = ViewModelProvider(
            this,
            VehicleViewModelFactory(vehicleDao)
        )[VehicleViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            VehicleservicelogTheme {
                AppNavHost(viewModel)
            }
        }
    }
}


@Composable
fun VehicleFormScreen(viewModel: VehicleViewModel, navController: NavHostController) {
    val vehicles by viewModel.allVehicles.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { },
        content = { paddingValues ->
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(text = "Model: ${vehicle.model}", fontWeight = FontWeight.Bold)
                            Text(text = "Licence Plate: ${vehicle.licencePlate}")
                            Text(text = "Type: ${vehicle.type}")
                            Text(text = "Active: ${if (vehicle.active) "Yes" else "No"}")
                        }
                    }
                }

                Button(
                    onClick = { navController.navigate("add_vehicle") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Go to Add Vehicle")

                }
            }
        }
    )
}

@Composable
fun AddVehicleScreen(viewModel: VehicleViewModel, navController: NavHostController) {

    var model by remember { mutableStateOf("") }
    var licencePlate by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Add New Vehicle", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = model,
                    onValueChange = { model = it },
                    label = { Text("Model") }
                )

                OutlinedTextField(
                    value = licencePlate,
                    onValueChange = { licencePlate = it },
                    label = { Text("Licence Plate") }
                )

                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Type") }
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = active,
                        onCheckedChange = { active = it }
                    )
                    Text(text = "Active")
                }

                Button(
                    onClick = {
                        if (model.isNotEmpty() && licencePlate.isNotEmpty() && type.isNotEmpty()) {
                            val newVehicle = Vehicle(
                                model = model,
                                licencePlate = licencePlate,
                                type = type,
                                active = active
                            )
                            viewModel.insert(newVehicle)
                        }

                        navController.navigate("main")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Vehicle")
                }
            }
        }
    )
}

@Composable
fun AppNavHost(viewModel: VehicleViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { VehicleFormScreen(viewModel, navController) }
        composable("add_vehicle") { AddVehicleScreen(viewModel, navController) }
    }
}