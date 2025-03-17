package com.thodoris.kotoufos.vehicle_service_log.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thodoris.kotoufos.vehicle_service_log.data.models.ServiceLog

@Composable
fun ServiceLogItem(log: ServiceLog, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Row {
                Text(
                    text = "Service Date: ", fontSize = 18.sp, fontWeight = FontWeight.Bold
                )
                Text(
                    text = log.date, fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row {
                Text(
                    text = "Shop: ", fontSize = 18.sp, fontWeight = FontWeight.Bold
                )
                Text(
                    text = log.shop, fontSize = 18.sp
                )
            }
        }

    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Gray
    )
}
