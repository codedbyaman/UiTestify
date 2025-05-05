package com.uitestify.ui.screens.accessibility

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uitestify.R
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@Composable
fun AccessibilityScreen(navController: NavController) {
    var isToggled by remember { mutableStateOf(false) }
    var isLargeText by remember { mutableStateOf(false) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Accessibility") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Accessibility Demonstration",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .semantics { contentDescription = "Accessibility demo title" }
            )

            Image(
                painter = painterResource(id = R.drawable.splash_screen),
                contentDescription = "UiTestify app splash image",
                modifier = Modifier
                    .size(140.dp)
                    .semantics { contentDescription = "App splash illustration" }
            )

            Text(
                text = "Switch Control (TalkBack Friendly)",
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                checked = isToggled,
                onCheckedChange = { isToggled = it },
                modifier = Modifier
                    .semantics {
                        contentDescription = if (isToggled) "Switch is ON" else "Switch is OFF"
                    }
            )

            Divider(thickness = 1.dp)

            Text("Text Size Preview", style = MaterialTheme.typography.titleMedium)

            Text(
                text = "This text scales with toggle below",
                fontSize = if (isLargeText) 24.sp else 16.sp,
                modifier = Modifier
                    .semantics {
                        contentDescription = "Scalable text for accessibility"
                    }
            )

            Button(
                onClick = { isLargeText = !isLargeText },
                modifier = Modifier.semantics {
                    contentDescription = "Toggle large text button"
                }
            ) {
                Text(if (isLargeText) "Use Normal Text" else "Use Large Text")
            }
        }
    }
}