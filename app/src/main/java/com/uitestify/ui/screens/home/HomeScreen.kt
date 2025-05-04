package com.uitestify.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import com.uitestify.ui.viewmodel.ThemeViewModel
import com.uitestify.util.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val KEY_EMAIL = stringPreferencesKey("email")
val KEY_PASSWORD = stringPreferencesKey("password")

data class TestFeature(
    val title: String,
    val description: String,
    val route: String
)

@Composable
fun HomeScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Load saved form values
    LaunchedEffect(Unit) {
        val prefs = context.dataStore.data.first()
        email = prefs[KEY_EMAIL] ?: ""
        password = prefs[KEY_PASSWORD] ?: ""
    }

    var isProfileTapped by remember { mutableStateOf(false) }

    val featureList = listOf(
        TestFeature("Form Validation", "Test field validation logic", "form"),
        TestFeature("List Swipe", "Swipe actions on items", "list"),
        TestFeature("Dialogs", "Alert and bottom sheet dialogs", "dialogs"),
        TestFeature("Async Flow", "Progress, error & success states", "async"),
        TestFeature("Accessibility", "Screen reader, talkback", "accessibility"),
        TestFeature("Localization", "Multi-language support", "localization"),
        TestFeature("Network State", "Offline / online behaviors", "network"),
        TestFeature("Dark Mode", "Theming and contrast", "darkmode"),
        TestFeature("Notifications", "Toast, snackbar, system notif", "notification"),
        TestFeature("File Upload", "Picker & upload handling", "fileupload"),
        TestFeature("Crash Test", "Simulate app crash safely", "crash"),
        TestFeature("Update Prompt", "App version alert UX", "update"),
        TestFeature("System Events", "Back/foreground/resume test", "system"),
        TestFeature("Deep Link Test", "External intent testing", "deeplink"),
        TestFeature("UI Playground", "All-in-one interaction zone", "playground")
    )

    GradientScaffold(
        topBar = { UiTestifyTopBar("UiTestify") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Avatar + Login Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { isProfileTapped = !isProfileTapped }
                        .testTag("card_avatar"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        if (isProfileTapped) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("👨‍💻", style = MaterialTheme.typography.headlineSmall)
                                Text("Aman Kumar", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    "codedbyaman@example.com",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        } else {
                            Text("👤", style = MaterialTheme.typography.headlineSmall)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_input_email")
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon =
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(icon, contentDescription = "Toggle password")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_input_password")
                    )

                    Button(
                        onClick = {
                            isDialogOpen = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_btn_login")
                    ) {
                        Text("Login")
                    }
                }
            }

            // Scrollable Feature Section
            Text(
                "Available Test Scenarios:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(featureList) { feature ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate(feature.route) }
                                .testTag("feature_card_${feature.route}")
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = feature.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = feature.description,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Login Dialog
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { isDialogOpen = false },
            confirmButton = {
                Button(onClick = {
                    error = when {
                        email.isBlank() || password.isBlank() -> "Fields cannot be empty"
                        !email.contains("@") -> "Invalid email format"
                        else -> null
                    }
                    if (error == null) {
                        scope.launch {
                            context.dataStore.edit {
                                it[KEY_EMAIL] = email
                                it[KEY_PASSWORD] = password
                            }
                        }
                        isDialogOpen = false
                        navController.navigate("login")
                    }
                }) {
                    Text("Login")
                }
            },
            title = { Text("Login") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon =
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(icon, contentDescription = "Toggle password")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (error != null) {
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        )
    }
}