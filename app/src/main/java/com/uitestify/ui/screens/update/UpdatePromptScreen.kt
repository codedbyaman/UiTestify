package com.uitestify.ui.screens.update

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePromptScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var updateStatus by remember { mutableStateOf("No update required") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var isDownloading by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

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
            Icon(
                imageVector = Icons.Default.SystemUpdate,
                contentDescription = "Update icon",
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Simulates a non-dismissible update with download progress.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("txt_update_info")
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        updateStatus = "Checking for updates..."
                        delay(1000)
                        updateStatus = "🚨 Update available"
                        showBottomSheet = true
                    }
                },
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
    }

    // Bottom Sheet with download simulation
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {},
            sheetState = bottomSheetState,
            dragHandle = null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🚀 App Update Available", style = MaterialTheme.typography.titleLarge)
                Text("Version 2.1 includes important improvements and fixes.")

                if (isDownloading) {
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("progress_bar")
                    )
                    Text("${(progress * 100).toInt()}%", modifier = Modifier.testTag("progress_percent"))
                } else {
                    Button(
                        onClick = {
                            isDownloading = true
                            progress = 0f
                            coroutineScope.launch {
                                while (progress < 1f) {
                                    delay(300)
                                    progress += 0.1f
                                }
                                showBottomSheet = false
                                updateStatus = "✅ Update completed"
                                isDownloading = false
                            }
                        },
                        modifier = Modifier.testTag("btn_force_update")
                    ) {
                        Text("Download Update")
                    }
                }
            }
        }
    }
}