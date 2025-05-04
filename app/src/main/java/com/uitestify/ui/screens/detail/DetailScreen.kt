package com.uitestify.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@Composable
fun DetailScreen(id: String?) {
    GradientScaffold(
        topBar = { UiTestifyTopBar("Detail Screen") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Navigated via Deep Link",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Received ID: ${id ?: "N/A"}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.testTag("txt_detail_id")
            )
        }
    }
}
