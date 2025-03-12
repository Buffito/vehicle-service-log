package com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.ui.components.VehicleItem
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiclesScreen(vehicleViewModel: VehicleViewModel, navController: NavHostController) {
    val vehicles by vehicleViewModel.allVehicles.collectAsState(emptyList())
    vehicleViewModel.addDefaultVehicleTypes()

    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showActiveOnly by remember { mutableStateOf(false) }

    val filteredVehicles = vehicles.filter { vehicle ->
        val matchesSearch =
            vehicle.make.contains(searchQuery, ignoreCase = true) || vehicle.model.contains(
                searchQuery,
                ignoreCase = true
            ) || vehicle.licencePlate.contains(searchQuery, ignoreCase = true)

        val matchesActiveFilter = !showActiveOnly || vehicle.active

        matchesSearch && matchesActiveFilter
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text("Vehicle List") }, actions = {
            IconButton(onClick = {
                showSearch = !showSearch
                if (!showSearch) searchQuery = ""
            }) {
                Icon(
                    imageVector = if (showSearch) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = if (showSearch) "Close Search" else "Search"
                )
            }
        })
    }, floatingActionButton = {
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
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (showSearch) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(16.dp)),
                            placeholder = { Text("Search Vehicles") },
                            trailingIcon = {
                                Icon(imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    modifier = Modifier.clickable {
                                        showSearch = false
                                        searchQuery = ""
                                    })
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Show Active Vehicles", modifier = Modifier.weight(1f)
                            )
                            Switch(checked = showActiveOnly, onCheckedChange = { isChecked ->
                                showActiveOnly = isChecked
                            })
                        }
                    }
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

