package com.uitestify.ui.screens.darkmode

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@Composable
fun DarkModeScreen(navController: NavController) {
    var isDarkMode by remember { mutableStateOf(false) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Dark Mode") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dark Mode Preview",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDarkMode) Color.Black else Color.White
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isDarkMode) "🌙 Dark Mode Active" else "☀️ Light Mode Active",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isDarkMode) Color.White else Color.Black,
                        modifier = Modifier.testTag("theme_status")
                    )
                }
            }

            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Enable Dark Mode")
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isDarkMode = it },
                    modifier = Modifier.testTag("theme_toggle")
                )
            }

            Text("This toggle simulates visual theme change for UI testing.")
        }
    }
}
