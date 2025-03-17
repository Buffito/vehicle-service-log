package com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.ui.components.EmptyListItem
import com.thodoris.kotoufos.vehicle_service_log.ui.components.ServiceLogItem
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceLogScreen(
    vehicleId: Int, viewModel: ServiceLogViewModel, navController: NavHostController
) {
    val serviceLogs by viewModel.allServiceLogsForVehicle(vehicleId).collectAsState(emptyList())

    var searchQuery by remember { mutableStateOf("") }

    val filteredServiceLogs = serviceLogs.filter {
        it.date.contains(searchQuery, ignoreCase = true) || it.shop.contains(
            searchQuery, ignoreCase = true
        ) || it.description.contains(searchQuery, ignoreCase = true)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    expanded = false,
                    onExpandedChange = {},
                    inputField = {
                        SearchBarDefaults.InputField(modifier = Modifier.fillMaxWidth(),
                            onSearch = { keyboardController?.hide() },
                            placeholder = { Text("Search Log") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Clear search"
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
            },
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate("service/-1/$vehicleId") },
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Service Log")
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (filteredServiceLogs.isEmpty()) {
                item {
                    EmptyListItem(text = "No service logs in list.")
                }
            } else {
                items(filteredServiceLogs) { log ->
                    ServiceLogItem(log = log) {
                        navController.navigate("service_info/${log.id}")
                    }
                }
                item {
                    Text(
                        text = "Tip: Press on a log to view additional information!",
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
    }
}
