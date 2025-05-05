package com.uitestify.ui.screens.crash

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

enum class CrashType(val label: String) {
    RUNTIME("Runtime Exception"),
    NULL_POINTER("Null Pointer Exception"),
    ARITHMETIC("Divide by Zero"),
    CUSTOM("Custom Crash")
}

@Composable
fun CrashTestScreen(navController: NavController) {
    var selectedCrash by remember { mutableStateOf(CrashType.RUNTIME) }
    var showDialog by remember { mutableStateOf(false) }
    var shouldCrash by remember { mutableStateOf(false) }

    // Launch crash only when confirmed
    LaunchedEffect(shouldCrash) {
        if (shouldCrash) {
            when (selectedCrash) {
                CrashType.RUNTIME -> throw RuntimeException("💥 Simulated RuntimeException")
                CrashType.NULL_POINTER -> {
                    val str: String? = null
                    Log.d("CrashTest", str!!.length.toString())
                }
                CrashType.ARITHMETIC -> {
                    val result = 1 / 0
                    Log.d("CrashTest", result.toString())
                }
                CrashType.CUSTOM -> error("💣 Boom! This is a custom crash.")
            }
        }
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Crash Test") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.BugReport,
                contentDescription = "Bug icon",
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "Use this screen to simulate crashes and test crash logging or handlers.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("txt_crash_info")
            )

            // Crash type selector
            Text("Select Crash Type:")
            CrashType.values().forEach { type ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedCrash == type,
                        onClick = { selectedCrash = type },
                        modifier = Modifier.testTag("radio_${type.name.lowercase()}")
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(type.label)
                }
            }

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.testTag("btn_trigger_crash")
            ) {
                Text("Trigger Crash")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirm Crash") },
                    text = { Text("This will intentionally crash the app.\nDo you want to continue?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                                shouldCrash = true
                            },
                            modifier = Modifier.testTag("btn_crash_confirm")
                        ) {
                            Text("Yes, Crash")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false },
                            modifier = Modifier.testTag("btn_crash_cancel")
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}