package com.thodoris.kotoufos.vehicle_service_log.ui.screens.vehicles

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.ui.components.EmptyListItem
import com.thodoris.kotoufos.vehicle_service_log.ui.components.VehicleItem
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiclesScreen(vehicleViewModel: VehicleViewModel, navController: NavHostController) {
    val vehicles by vehicleViewModel.allVehicles.collectAsState(emptyList())
    vehicleViewModel.addDefaultVehicleTypes()

    var searchQuery by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val filteredVehicles = vehicles.filter { vehicle ->
        val matchesSearch =
            vehicle.make.contains(searchQuery, ignoreCase = true) || vehicle.model.contains(
                searchQuery, ignoreCase = true
            ) || vehicle.licencePlate.contains(searchQuery, ignoreCase = true)

        matchesSearch
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                expanded = false,
                onExpandedChange = { },
                inputField = {
                    SearchBarDefaults.InputField(modifier = Modifier.fillMaxWidth(),
                        onSearch = { keyboardController?.hide() },
                        placeholder = { Text("Search vehicles") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search, contentDescription = null
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        Icons.Default.Close, contentDescription = "Clear search"
                                    )
                                }
                            }
                        },
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        expanded = false,
                        onExpandedChange = {})
                },
                content = {},
                tonalElevation = 0.dp
            )
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate("vehicle/-1") },
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Vehicle")
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            if (filteredVehicles.isEmpty()) {
                item {
                    EmptyListItem(text = "No vehicles found")
                }
            } else {
                items(filteredVehicles) { vehicle ->
                    VehicleItem(vehicle = vehicle) {
                        navController.navigate("vehicle_info/${vehicle.id}")
                    }
                }
            }
        }
    }
}

