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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

sealed class AsyncState {
    object Idle : AsyncState()
    object Loading : AsyncState()
    object Success : AsyncState()
    data class Error(val message: String) : AsyncState()
}

@Composable
fun AsyncFlowScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var state: AsyncState by remember { mutableStateOf(AsyncState.Idle) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Async Flow") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val currentState = state) {
                is AsyncState.Idle -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Press the button to simulate an async task.")
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                state = AsyncState.Loading
                                coroutineScope.launch {
                                    delay(2000)
                                    val isSuccess = (0..1).random() == 0
                                    state = if (isSuccess) {
                                        AsyncState.Success
                                    } else {
                                        AsyncState.Error("Something went wrong!")
                                    }
                                }
                            }
                        ) {
                            Text("Start Async Operation")
                        }
                    }
                }

                is AsyncState.Loading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Loading...")
                    }
                }

                is AsyncState.Success -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✅ Operation Successful", color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { state = AsyncState.Idle }) {
                            Text("Reset")
                        }
                    }
                }

                is AsyncState.Error -> {
                    LaunchedEffect(currentState.message) {
                        snackbarHostState.showSnackbar(currentState.message)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❌ Error Occurred", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { state = AsyncState.Idle }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}