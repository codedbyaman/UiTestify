package com.uitestify.ui.screens.deeplink

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
