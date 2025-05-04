package com.uitestify.ui.screens.crash

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
fun CrashTestScreen(navController: NavController) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var shouldCrash by remember { mutableStateOf(false) }

    // Trigger crash in side effect for real crash simulation
    LaunchedEffect(shouldCrash) {
        if (shouldCrash) {
            throw RuntimeException("💥 Simulated crash for UI testing")
        }
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Crash Test") }
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
                text = "This screen simulates a crash to test stability & logging.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("txt_crash_info")
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier.testTag("btn_request_crash")
            ) {
                Text("Trigger Simulated Crash")
            }

            if (showConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog = false },
                    title = { Text("Are you sure?") },
                    text = { Text("Triggering this will crash the app intentionally.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showConfirmDialog = false
                                shouldCrash = true
                            },
                            modifier = Modifier.testTag("btn_confirm_crash")
                        ) {
                            Text("Crash Now")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { showConfirmDialog = false },
                            modifier = Modifier.testTag("btn_cancel_crash")
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
