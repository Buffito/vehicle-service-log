package com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.ui.components.VehicleItem
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel

@Composable
fun VehiclesScreen(vehicleViewModel: VehicleViewModel, navController: NavHostController) {
    val vehicles by vehicleViewModel.allVehicles.collectAsState(emptyList())
    vehicleViewModel.addDefaultVehicleTypes()
    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredVehicles = vehicles.filter {
        it.make.contains(searchQuery, ignoreCase = true) || it.model.contains(
            searchQuery, ignoreCase = true
        ) || it.licencePlate.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { }, floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate("vehicle/-1") },
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Vehicle")
        }
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showSearch) {
                    TextField(value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search Vehicles") },
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                modifier = Modifier.clickable {
                                    showSearch = false; searchQuery = ""
                                })
                        })

                } else {
                    Text(
                        text = "Vehicle List", fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )

                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { showSearch = true })
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f, fill = false)
            ) {
                if (vehicles.isEmpty()) {
                    item {
                        Text(
                            text = "No vehicle in list.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            fontSize = 16.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }

                    return@LazyColumn
                }

                items(filteredVehicles) { vehicle ->
                    VehicleItem(vehicle = vehicle, onClick = {
                        navController.navigate("vehicle_info/${vehicle.id}")
                    })
                }

                item {
                    Text(
                        text = "Tip: Press on a vehicle to view additional information!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }


        }
    })
}