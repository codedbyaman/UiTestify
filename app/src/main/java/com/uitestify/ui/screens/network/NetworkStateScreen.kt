package com.uitestify.ui.screens.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

@Composable
fun NetworkStateScreen(navController: NavController) {
    val context = LocalContext.current
    var isConnected by remember { mutableStateOf(false) }

    fun checkConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    LaunchedEffect(Unit) {
        isConnected = checkConnection()
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
                onClick = { isConnected = checkConnection() },
                modifier = Modifier.testTag("check_network_button")
            ) {
                Text("Re-check Connectivity")
            }
        }
    }
}
