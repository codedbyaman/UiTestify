package com.uitestify.ui.screens.async

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AsyncFlowScreen(navController: NavController) {
    var state by remember { mutableStateOf("idle") }
    val coroutineScope = rememberCoroutineScope()

    GradientScaffold(
        topBar = { UiTestifyTopBar("Async Flow") }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                "idle" -> {
                    Button(onClick = {
                        state = "loading"
                        coroutineScope.launch {
                            delay(2000) // simulate async delay
                            state = if ((0..1).random() == 0) "success" else "error"
                        }
                    }) {
                        Text("Start Async Operation")
                    }
                }

                "loading" -> {
                    CircularProgressIndicator()
                }

                "success" -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✅ Operation Successful")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { state = "idle" }) {
                            Text("Reset")
                        }
                    }
                }

                "error" -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❌ Operation Failed", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { state = "idle" }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}
