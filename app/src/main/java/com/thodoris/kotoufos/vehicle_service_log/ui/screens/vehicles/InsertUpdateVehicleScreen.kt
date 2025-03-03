package com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.data.models.Vehicle
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel

@Composable
fun InsertUpdateVehicleScreen(
    vehicleId: Int, vehicleViewModel: VehicleViewModel, navController: NavHostController
) {
    val vehicle by vehicleViewModel.vehicleById(vehicleId).collectAsState(initial = null)
    val vehicleTypes by vehicleViewModel.allVehicleTypes.collectAsState(emptyList())

    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var licencePlate by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }


    LaunchedEffect(vehicle) {
        if (vehicle != null) {
            make = vehicle!!.make
            model = vehicle!!.model
            licencePlate = vehicle!!.licencePlate
            type = vehicle!!.type
            active = vehicle!!.active
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (vehicleId == -1) "Add New Vehicle" else "Edit Vehicle",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(value = make,
                onValueChange = { make = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Make") })

            OutlinedTextField(value = model,
                onValueChange = { model = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Model") })

            OutlinedTextField(value = licencePlate,
                onValueChange = { licencePlate = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Licence Plate") })

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clickable { expanded = true }
                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
                .padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = type.ifEmpty { "Select Type" },
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                    Icon(Icons.Default.ArrowDropDown, "Dropdown")
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
                            id = if (vehicleId == -1) 0 else vehicleId,
                            make = make,
                            model = model,
                            licencePlate = licencePlate,
                            type = type,
                            active = active
                        )

                        if (vehicleId == -1) {
                            vehicleViewModel.insertVehicle(newVehicle)
                        } else {
                            vehicleViewModel.updateVehicle(newVehicle)
                        }

                        navController.popBackStack()

                    }
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (vehicleId == -1) "Save Vehicle" else "Update Vehicle")
            }
        }
    })
}
