package com.uitestify.ui.screens.accessibility

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.R
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@Composable
fun AccessibilityScreen(navController: NavController) {
    var toggle by remember { mutableStateOf(false) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Accessibility") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Accessibility Demo", style = MaterialTheme.typography.headlineSmall)

            Image(
                painter = painterResource(id = R.drawable.splash_screen),
                contentDescription = "UiTestify Logo",
                modifier = Modifier
                    .size(120.dp)
                    .semantics { contentDescription = "UiTestify splash image" }
            )

            Text("Toggle below with TalkBack on")
            Switch(
                checked = toggle,
                onCheckedChange = { toggle = it },
                modifier = Modifier.semantics {
                    contentDescription = if (toggle) "On" else "Off"
                }
            )
        }
    }
}
