package com.uitestify.ui.screens.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(navController: NavController) {
    var showAlert by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Dialogs") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { showAlert = true }) {
                Text("Show AlertDialog")
            }

            Button(onClick = { showBottomSheet = true }) {
                Text("Show BottomSheet")
            }
        }
    }

    // Alert Dialog
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Exit App") },
            text = { Text("Are you sure you want to exit?") },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text("No")
                }
            }
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("This is a BottomSheet")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showBottomSheet = false }) {
                    Text("Dismiss")
                }
            }
        }
    }
}
