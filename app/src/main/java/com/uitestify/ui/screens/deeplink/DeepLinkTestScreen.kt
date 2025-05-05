package com.uitestify.ui.screens.deeplink

import android.net.Uri
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
import kotlinx.coroutines.launch

@Composable
fun DeepLinkTestScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var simulatedUri by remember { mutableStateOf<Uri?>(null) }
    var extractedParams by remember { mutableStateOf("") }

    fun simulateUri(uriString: String) {
        val uri = Uri.parse(uriString)
        simulatedUri = uri
        val id = uri.getQueryParameter("id")
        val source = uri.getQueryParameter("source")
        extractedParams = buildString {
            appendLine("• id: ${id ?: "null"}")
            appendLine("• source: ${source ?: "null"}")
        }

        coroutineScope.launch {
            snackbarHostState.showSnackbar("Deep link simulated.")
        }
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Deep Link Test") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Simulate a deep link with query parameters:")

            Button(
                onClick = {
                    simulateUri("uitestify://open/details?id=42&source=test")
                },
                modifier = Modifier.testTag("btn_simulate_deeplink_42")
            ) {
                Text("Simulate ID = 42")
            }

            Button(
                onClick = {
                    simulateUri("uitestify://open/details?id=100&source=notification")
                },
                modifier = Modifier.testTag("btn_simulate_deeplink_100")
            ) {
                Text("Simulate ID = 100")
            }

            OutlinedButton(
                onClick = {
                    simulatedUri = null
                    extractedParams = ""
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Deep link reset.")
                    }
                },
                modifier = Modifier.testTag("btn_reset_deeplink")
            ) {
                Text("Reset")
            }

            Divider()

            Text(
                text = "URI:",
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = simulatedUri?.toString() ?: "No deep link simulated yet.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("txt_deeplink_uri")
            )

            if (extractedParams.isNotBlank()) {
                Divider()
                Text(
                    text = "Extracted Params:",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = extractedParams,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.testTag("txt_deeplink_params")
                )
            }
        }
    }
}