package com.uitestify.ui.screens.notification

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.launch

@Composable
fun NotificationScreen(navController: NavController) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Notification Showcase") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "UI Notifications",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.testTag("title_notifications")
            )

            // Snackbar button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("📩 Snackbar", style = MaterialTheme.typography.labelLarge)
                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("✅ Snackbar shown successfully")
                        }
                    },
                    modifier = Modifier.testTag("btn_snackbar")
                ) {
                    Text("Show Snackbar")
                }
            }

            // Toast button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("📢 Toast", style = MaterialTheme.typography.labelLarge)
                Button(
                    onClick = {
                        Toast.makeText(context, "🔔 This is a Toast", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.testTag("btn_toast")
                ) {
                    Text("Show Toast")
                }
            }

            // AlertDialog button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("⚠️ Alert Dialog", style = MaterialTheme.typography.labelLarge)
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.testTag("btn_alertdialog")
                ) {
                    Text("Show Dialog")
                }
            }

            // Simulate System Notification (Mock only)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🔔 System Notification", style = MaterialTheme.typography.labelLarge)
                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Simulating system notification...",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.testTag("btn_mock_notification")
                ) {
                    Text("Mock Notification")
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirmation Required") },
                    text = { Text("Are you sure you want to proceed with this action?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("✅ Confirmed")
                                }
                            }
                        ) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}