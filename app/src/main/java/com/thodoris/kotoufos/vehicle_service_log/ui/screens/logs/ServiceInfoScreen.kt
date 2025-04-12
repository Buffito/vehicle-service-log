package com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.ui.components.DeleteConfirmationDialog
import com.thodoris.kotoufos.vehicle_service_log.ui.components.InfoRow
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceInfoScreen(
    logId: Int, serviceLogViewModel: ServiceLogViewModel, navController: NavHostController
) {
    val serviceLog by serviceLogViewModel.logById(logId).collectAsState(initial = null)
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Service Information",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            serviceLog?.let { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoRow(label = "Service Date", value = log.date)
                        InfoRow(label = "Shop", value = log.shop)
                        InfoRow(label = "Mileage", value = log.mileage.toString())
                        InfoRow(label = "Description", value = log.description)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { navController.navigate("service/${log.id}/${log.vehicleId}") }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(onClick = { showDialog = true }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            } ?: Text("Service log not found.")

            Spacer(modifier = Modifier.weight(1f))
        }
    }


    if (showDialog) {
        DeleteConfirmationDialog(onDismiss = { showDialog = false },
            title = "Delete Service Log",
            text = "Are you sure you want to delete this log?",
            onConfirm = {
                showDialog = false
                serviceLog?.let {
                    serviceLogViewModel.deleteServiceLog(it)
                    navController.popBackStack()
                }
            })
    }
}