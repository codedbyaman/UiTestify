package com.uitestify.ui.screens.crash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
