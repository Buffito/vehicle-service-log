package com.thodoris.kotoufos.vehicle_service_log.ui.screens.logs

import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.thodoris.kotoufos.vehicle_service_log.data.models.ServiceLog
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.ServiceLogViewModel
import com.thodoris.kotoufos.vehicle_service_log.ui.viewmodel.VehicleViewModel
import java.util.Calendar

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

    val vehicleText =
        "${vehicle?.make ?: ""} ${vehicle?.model ?: ""} (${vehicle?.licencePlate ?: ""})"

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
        if (service != null) {
            serviceDate = service!!.date
            shop = service!!.shop
            vehicleMileage = service!!.mileage.toString()
            description = service!!.description
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (serviceId == -1) "Add Service for $vehicleText" else "Edit Service for $vehicleText",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(serviceDate.ifEmpty { "Select Date" })
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
                }
            }

            OutlinedTextField(value = shop,
                onValueChange = { shop = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Shop") })

            OutlinedTextField(value = vehicleMileage,
                onValueChange = { vehicleMileage = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Vehicle Mileage") })

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                label = { Text("Description") },
                maxLines = 5
            )

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
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (serviceId == -1) "Save to Log" else "Update Log")
            }
        }
    })
}