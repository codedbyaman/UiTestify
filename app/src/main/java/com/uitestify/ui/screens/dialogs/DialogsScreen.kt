package com.uitestify.ui.screens.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }

    var showAlert by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showMultiOptionDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    var selectedChoice by remember { mutableStateOf<String?>(null) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Dialogs & Sheets") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Explore Dialogs", style = MaterialTheme.typography.titleMedium)

            Button(onClick = { showAlert = true }, modifier = Modifier.testTag("btn_alert")) {
                Text("Show Alert")
            }

            Button(onClick = { showConfirm = true }, modifier = Modifier.testTag("btn_confirm")) {
                Text("Show Confirm Dialog")
            }

            Button(onClick = { showBottomSheet = true }, modifier = Modifier.testTag("btn_bottom_sheet")) {
                Text("Show Bottom Sheet")
            }

            Button(onClick = { showMultiOptionDialog = true }, modifier = Modifier.testTag("btn_multi_choice")) {
                Text("Show Multi-Option Dialog")
            }

            Button(onClick = { showInfoDialog = true }, modifier = Modifier.testTag("btn_info_dialog")) {
                Text("Show Info Dialog")
            }

            selectedChoice?.let {
                Text("🟢 You selected: $it", modifier = Modifier.testTag("txt_selected_option"))
            }
        }
    }

    // --- Alert Dialog ---
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Heads up!") },
            text = { Text("This is a simple alert message.") },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) { Text("OK") }
            }
        )
    }

    // --- Confirm Dialog ---
    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Sign Out?") },
            text = { Text("Are you sure you want to sign out?") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirm = false
                    selectedChoice = "Signed out"
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) { Text("No") }
            }
        )
    }

    // --- Modal Bottom Sheet with input ---
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            modifier = Modifier.testTag("bottom_sheet")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Enter a value:")

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Your Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_name")
                )

                Button(
                    onClick = {
                        selectedChoice = nameInput
                        showBottomSheet = false
                        nameInput = ""
                    },
                    modifier = Modifier.testTag("btn_confirm_input")
                ) {
                    Text("Submit")
                }
            }
        }
    }

    // --- Multi Option Dialog ---
    if (showMultiOptionDialog) {
        AlertDialog(
            onDismissRequest = { showMultiOptionDialog = false },
            title = { Text("Choose an option") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Option A", "Option B", "Option C").forEach { option ->
                        TextButton(
                            onClick = {
                                selectedChoice = option
                                showMultiOptionDialog = false
                            },
                            modifier = Modifier.testTag("choice_$option")
                        ) {
                            Text(option)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMultiOptionDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    // --- Info Dialog with Icon ---
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            title = { Text("Info") },
            text = { Text("This is an informational dialog with an icon.") },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("Got it")
                }
            }
        )
    }
}