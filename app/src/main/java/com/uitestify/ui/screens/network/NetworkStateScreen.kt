package com.uitestify.ui.screens.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NetworkStateScreen(
    navController: NavController,
    viewModel: NetworkViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isAutoRefresh by viewModel.autoRefresh.collectAsState(initial = false)

    var isConnected by remember { mutableStateOf(false) }

    fun checkConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Initial & auto-refresh check
    LaunchedEffect(isAutoRefresh) {
        if (isAutoRefresh) {
            while (true) {
                isConnected = checkConnection()
                delay(3000)
            }
        } else {
            isConnected = checkConnection()
        }
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Network State") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isConnected) "✅ Connected to Internet" else "❌ No Internet Connection",
                modifier = Modifier.testTag("network_status"),
                style = MaterialTheme.typography.headlineSmall,
                color = if (isConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isConnected = checkConnection()
                },
                modifier = Modifier.testTag("check_network_button")
            ) {
                Text("Manual Check")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Auto Refresh", modifier = Modifier.padding(end = 8.dp))
                Switch(
                    checked = isAutoRefresh,
                    onCheckedChange = { scope.launch { viewModel.setAutoRefresh(it) } },
                    modifier = Modifier.testTag("auto_refresh_toggle")
                )
            }
        }
    }
}