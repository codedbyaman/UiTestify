package com.uitestify.ui.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    var showResetDialog by remember { mutableStateOf(false) }
    var resetEmail by remember { mutableStateOf("") }
    var resetError by remember { mutableStateOf<String?>(null) }

    // Simulate login delay and navigate on success
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2000)
            isLoading = false
            navController.navigate("dashboard?email=${email.trim()}") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Login Screen") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Please sign in to continue", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_email")
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                enabled = !isLoading,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon =
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        enabled = !isLoading
                    ) {
                        Icon(imageVector = icon, contentDescription = "Toggle Password")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_password")
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    enabled = !isLoading,
                    modifier = Modifier.testTag("checkbox_remember")
                )
                Text("Remember Me")
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable(enabled = !isLoading) {
                            showResetDialog = true
                        }
                        .testTag("forgot_password")
                )
            }

            Button(
                onClick = {
                    error = when {
                        email.isBlank() || password.isBlank() -> "Fields cannot be empty"
                        !email.contains("@") -> "Invalid email format"
                        else -> null
                    }
                    if (error == null) isLoading = true
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_login")
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .testTag("progress_login"),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login")
                }
            }

            OutlinedButton(
                onClick = { error = "Biometric login not available" },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_biometric")
            ) {
                Text("Login with Fingerprint")
            }

            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.testTag("txt_error")
                )
            }

            if (showResetDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showResetDialog = false
                        resetEmail = ""
                        resetError = null
                    },
                    title = { Text("Reset Password") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = resetEmail,
                                onValueChange = { resetEmail = it },
                                label = { Text("Enter your email") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.testTag("input_reset_email")
                            )
                            if (resetError != null) {
                                Text(
                                    text = resetError!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            resetError = when {
                                resetEmail.isBlank() -> "Email required"
                                !resetEmail.contains("@") -> "Invalid email format"
                                else -> {
                                    showResetDialog = false
                                    resetEmail = ""
                                    resetError = null
                                    error = "Reset link sent to your email"
                                    null
                                }
                            }
                        }) {
                            Text("Submit")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showResetDialog = false
                            resetEmail = ""
                            resetError = null
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
