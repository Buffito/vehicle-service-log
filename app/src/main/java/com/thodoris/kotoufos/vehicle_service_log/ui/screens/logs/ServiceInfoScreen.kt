package com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.ui.components.DeleteConfirmationDialog
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModel

@Composable
fun ServiceInfoScreen(
    logId: Int, serviceLogViewModel: ServiceLogViewModel, navController: NavHostController
) {
    val serviceLog by serviceLogViewModel.logById(logId).collectAsState(initial = null)
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Service Information", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            serviceLog?.let { log ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Service Date: ${log.date}", fontSize = 14.sp)
                        Text(text = "Shop: ${log.shop}", fontSize = 14.sp)
                        Text(text = "Mileage: ${log.mileage}", fontSize = 14.sp)
                        Text(text = "Description: ${log.description}", fontSize = 14.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = { navController.navigate("service/${log.id}/${log.vehicleId}") }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { showDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        DeleteConfirmationDialog(title = "Delete Service Log",
            text = "Are you sure you want to delete this log?",
            onConfirm = {
                serviceLog?.let {
                    serviceLogViewModel.deleteServiceLog(it)
                    navController.popBackStack()
                }
                showDialog = false
            },
            onDismiss = { showDialog = false })
    }
}