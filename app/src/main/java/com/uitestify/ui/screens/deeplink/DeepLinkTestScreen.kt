package com.uitestify.ui.screens.deeplink

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import androidx.compose.ui.Alignment


@Composable
fun DeepLinkTestScreen(navController: NavController) {
    var simulatedUri by remember { mutableStateOf<Uri?>(null) }
    var extractedParams by remember { mutableStateOf("") }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Deep Link Test") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val uri = Uri.parse("uitestify://open/details?id=42&source=test")
                    simulatedUri = uri

                    val id = uri.getQueryParameter("id")
                    val source = uri.getQueryParameter("source")
                    extractedParams = "id=$id\nsource=$source"

                    // Optionally trigger navigation
                    // navController.navigate("details/$id")
                },
                modifier = Modifier.testTag("btn_simulate_deeplink")
            ) {
                Text("Simulate Deep Link")
            }

            Text(
                text = simulatedUri?.toString() ?: "No deep link received yet.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("txt_deeplink_uri")
            )

            if (extractedParams.isNotEmpty()) {
                Divider()

                Text(
                    text = "Extracted Params:\n$extractedParams",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.testTag("txt_deeplink_params")
                )
            }
        }
    }
}
