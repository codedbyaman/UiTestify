package com.uitestify.ui.screens.update

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@Composable
fun UpdatePromptScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var updateStatus by remember { mutableStateOf("No update required") }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Update Prompt") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "This screen simulates an app update prompt.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("txt_update_info")
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.testTag("btn_check_update")
            ) {
                Text("Check for Update")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = updateStatus,
                modifier = Modifier.testTag("txt_update_status"),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { /* Prevent dismiss */ },
                title = { Text("Update Available") },
                text = {
                    Text("A new version of the app is available. Please update to continue.")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            updateStatus = "✅ Update initiated (simulated)"
                        },
                        modifier = Modifier.testTag("btn_confirm_update")
                    ) {
                        Text("Update Now")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            showDialog = false
                            updateStatus = "❌ Update skipped"
                        },
                        modifier = Modifier.testTag("btn_skip_update")
                    ) {
                        Text("Skip")
                    }
                }
            )
        }
    }
}
