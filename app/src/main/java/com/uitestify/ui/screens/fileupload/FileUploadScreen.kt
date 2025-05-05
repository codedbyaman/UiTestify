package com.uitestify.ui.screens.fileupload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FileUploadScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var uploadStatus by remember { mutableStateOf("No file uploaded") }
    var isUploading by remember { mutableStateOf(false) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedFileUri = uri
        uploadStatus = if (uri != null) "📁 File selected" else "No file uploaded"
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("File Upload") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { filePickerLauncher.launch("*/*") },
                modifier = Modifier.testTag("btn_pick_file")
            ) {
                Text("Choose File")
            }

            selectedFileUri?.let { uri ->
                Text(
                    text = "Selected: ${uri.lastPathSegment ?: "Unknown"}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("txt_file_name")
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            isUploading = true
                            uploadStatus = "⏳ Uploading..."
                            coroutineScope.launch {
                                delay(2000)
                                uploadStatus = "✅ File uploaded successfully"
                                isUploading = false
                            }
                        },
                        enabled = !isUploading,
                        modifier = Modifier.testTag("btn_upload")
                    ) {
                        if (isUploading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(end = 8.dp),
                                strokeWidth = 2.dp
                            )
                        }
                        Text("Upload")
                    }

                    OutlinedButton(
                        onClick = {
                            selectedFileUri = null
                            uploadStatus = "No file uploaded"
                            isUploading = false
                        },
                        modifier = Modifier.testTag("btn_clear")
                    ) {
                        Text("Clear")
                    }
                }
            } ?: Text(
                text = "No file selected",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("txt_file_name")
            )

            Divider(modifier = Modifier.padding(top = 16.dp))

            Text(
                text = uploadStatus,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("txt_upload_status")
            )
        }
    }
}