package com.uitestify.ui.screens.notification

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
        topBar = { UiTestifyTopBar("Notifications") },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("This is a Snackbar")
                    }
                },
                modifier = Modifier.testTag("btn_snackbar")
            ) {
                Text("Show Snackbar")
            }

            Button(
                onClick = {
                    Toast.makeText(context, "This is a Toast", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.testTag("btn_toast")
            ) {
                Text("Show Toast")
            }

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.testTag("btn_alertdialog")
            ) {
                Text("Show Alert Dialog")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirmation") },
                    text = { Text("Are you sure you want to proceed?") },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
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
