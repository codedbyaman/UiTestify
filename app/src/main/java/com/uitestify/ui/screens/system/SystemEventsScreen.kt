package com.uitestify.ui.screens.system

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.launch

@Composable
fun SystemEventsScreen(navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var lifecycleState by remember { mutableStateOf("Created") }

    // Observe lifecycle and update state for UI test validation
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            val state = when (event) {
                Lifecycle.Event.ON_CREATE -> "Created"
                Lifecycle.Event.ON_START -> "Started"
                Lifecycle.Event.ON_RESUME -> "Resumed"
                Lifecycle.Event.ON_PAUSE -> "Paused"
                Lifecycle.Event.ON_STOP -> "Stopped"
                Lifecycle.Event.ON_DESTROY -> "Destroyed"
                else -> "Unknown"
            }
            lifecycleState = state

            // Optionally show transient message for UI feedback
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Lifecycle: $state")
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("System Events") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Monitor app lifecycle for UI test scenarios.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("txt_lifecycle_info")
            )

            Text(
                text = "Current lifecycle state:",
                style = MaterialTheme.typography.titleMedium
            )

            Surface(
                tonalElevation = 2.dp,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .testTag("surface_lifecycle_state")
            ) {
                Text(
                    text = lifecycleState,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(20.dp)
                        .testTag("txt_lifecycle_state")
                )
            }

            Text(
                text = "Try backgrounding, resuming or rotating the app.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
