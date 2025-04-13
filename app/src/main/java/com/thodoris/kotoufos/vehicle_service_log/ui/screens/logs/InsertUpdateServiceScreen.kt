package com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs

import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.data.models.ServiceLog
import com.thodoris.kotoufos.vehicle_service_log.ui.components.InputField
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModel
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun InsertUpdateServiceScreen(
    vehicleId: Int,
    serviceId: Int,
    serviceLogViewModel: ServiceLogViewModel,
    vehicleViewModel: VehicleViewModel,
    navController: NavHostController
) {
    val vehicle by vehicleViewModel.vehicleById(vehicleId).collectAsState(initial = null)
    val service by serviceLogViewModel.logById(serviceId).collectAsState(initial = null)
    val vehicleText = "${vehicle?.make ?: ""} ${vehicle?.model ?: ""}"

    var serviceDate by remember { mutableStateOf("") }
    var shop by remember { mutableStateOf("") }
    var vehicleMileage by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            serviceDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LaunchedEffect(service) {
        service?.let {
            serviceDate = it.date
            shop = it.shop
            vehicleMileage = it.mileage.toString()
            description = it.description
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = if (serviceId == -1) "Add Service for $vehicleText"
                    else "Edit Service for $vehicleText",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }, bottomBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding() + 16.dp,
                    top = 8.dp
                )
        ) {
            Button(
                onClick = {
                    if (serviceDate.isNotEmpty() && shop.isNotEmpty() && vehicleMileage.isNotEmpty()) {
                        val log = ServiceLog(
                            id = if (serviceId == -1) 0 else serviceId,
                            vehicleId = vehicleId,
                            date = serviceDate,
                            shop = shop,
                            mileage = vehicleMileage.toInt(),
                            description = description
                        )

                        if (serviceId == -1) {
                            serviceLogViewModel.insertServiceLog(log)
                        } else {
                            serviceLogViewModel.updateServiceLog(log)
                        }

                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(48.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                Icon(
                    imageVector = if (serviceId == -1) Icons.Default.Add else Icons.Default.Create,
                    contentDescription = if (serviceId == -1) "Save Log" else "Update Log",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(if (serviceId == -1) "Save to Log" else "Update Log")
            }
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
                .padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = serviceDate.ifEmpty { "Select Date" },
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                    Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                }
            }

            InputField("Shop", shop) { shop = it }
            InputField("Vehicle Mileage", vehicleMileage, KeyboardType.Number) {
                vehicleMileage = it
            }
            InputField("Description", description, maxLines = 5, height = 150.dp) {
                description = it
            }
        }
    }
}
