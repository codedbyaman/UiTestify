package com.uitestify.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to UiTestify")

        Button(onClick = { navController.navigate("form") }) { Text("Form Validation") }
        Button(onClick = { navController.navigate("list") }) { Text("List Swipe") }
        Button(onClick = { navController.navigate("dialogs") }) { Text("Dialogs") }
        Button(onClick = { navController.navigate("async") }) { Text("Async Flow") }
        Button(onClick = { navController.navigate("accessibility") }) { Text("Accessibility") }
        Button(onClick = { navController.navigate("localization") }) { Text("Localization") }
        Button(onClick = { navController.navigate("network") }) { Text("Network State") }
        Button(onClick = { navController.navigate("darkmode") }) { Text("Dark Mode") }
        Button(onClick = { navController.navigate("notification") }) { Text("Notifications") }
        Button(onClick = { navController.navigate("fileupload") }) { Text("File Upload") }
        Button(onClick = { navController.navigate("crash") }) { Text("Crash Test") }
        Button(onClick = { navController.navigate("update") }) { Text("Update Prompt") }
        Button(onClick = { navController.navigate("system") }) { Text("System Events") }
        Button(onClick = { navController.navigate("deeplink") }) { Text("Deep Link Test") }
    }
}