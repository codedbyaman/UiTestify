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
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var uploadStatus by remember { mutableStateOf("No file uploaded") }
    val coroutineScope = rememberCoroutineScope()

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedFileUri = uri
        uploadStatus = "File selected, ready to upload"
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("File Upload") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { filePickerLauncher.launch("*/*") },
                modifier = Modifier.testTag("btn_pick_file")
            ) {
                Text("Choose File")
            }

            if (selectedFileUri != null) {
                Text(
                    text = "Selected: ${selectedFileUri?.lastPathSegment}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("txt_file_name")
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                uploadStatus = "Uploading..."
                                delay(1500)
                                uploadStatus = "✅ File uploaded successfully"
                            }
                        },
                        modifier = Modifier.testTag("btn_upload")
                    ) {
                        Text("Upload")
                    }

                    OutlinedButton(
                        onClick = {
                            selectedFileUri = null
                            uploadStatus = "No file uploaded"
                        },
                        modifier = Modifier.testTag("btn_clear")
                    ) {
                        Text("Clear")
                    }
                }
            } else {
                Text(
                    text = "No file selected",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("txt_file_name")
                )
            }

            Divider()

            Text(
                text = uploadStatus,
                modifier = Modifier.testTag("txt_upload_status"),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
