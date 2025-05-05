package com.uitestify.ui.screens.system

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SystemEventsScreen(navController: NavController) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    var lifecycleState by remember { mutableStateOf("Created") }
    var lastUpdated by remember { mutableStateOf(currentTimestamp()) }
    val eventLog = remember { mutableStateListOf("Created at $lastUpdated") }

    // Observe lifecycle
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
            val timestamp = currentTimestamp()
            lifecycleState = state
            lastUpdated = timestamp
            eventLog.add("$state at $timestamp")

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Current lifecycle state card
            Surface(
                tonalElevation = 2.dp,
                color = when (lifecycleState) {
                    "Resumed" -> Color(0xFFB6EFBC)
                    "Paused" -> Color(0xFFFFF3B0)
                    "Destroyed" -> Color(0xFFFFCDD2)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = lifecycleState,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.testTag("txt_lifecycle_state")
                    )
                    Text("Last updated at: $lastUpdated")
                }
            }

            // Log actions
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { eventLog.clear() },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_clear_log")
                ) {
                    Text("Clear Log")
                }

                OutlinedButton(
                    onClick = {
                        val logText = eventLog.joinToString("\n")
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "System Event Log")
                            putExtra(Intent.EXTRA_TEXT, logText)
                        }
                        val chooser = Intent.createChooser(intent, "Share log via...")
                        context.startActivity(chooser)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_share_log")
                ) {
                    Text("Share Log")
                }
            }

            Text("Event History:", style = MaterialTheme.typography.titleMedium)

            // Scrollable event log
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("lazy_lifecycle_log"),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(eventLog.reversed()) { event ->
                    Surface(
                        tonalElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = event,
                            modifier = Modifier
                                .padding(12.dp)
                                .testTag("log_item_${event.hashCode()}"),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

private fun currentTimestamp(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
}