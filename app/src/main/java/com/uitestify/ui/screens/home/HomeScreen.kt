package com.uitestify.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Select Option") }
    var checkboxState by remember { mutableStateOf(false) }
    var switchState by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    val listItems = (1..10).map { "Item $it" }

    data class TestFeature(
        val title: String,
        val description: String,
        val route: String
    )

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


    Button(onClick = { navController.navigate("form") }) {
        Text("Form Validation")
    }
    Button(onClick = { navController.navigate("list") }) {
        Text("List Swipe")
    }
    Button(onClick = { navController.navigate("dialogs") }) {
        Text("Dialogs")
    }
    Button(onClick = { navController.navigate("async") }) {
        Text("Async Flow")
    }
    Button(onClick = { navController.navigate("accessibility") }) {
        Text("Accessibility")
    }
    Button(onClick = { navController.navigate("localization") }) {
        Text("Localization")
    }
    Button(onClick = { navController.navigate("network") }) {
        Text("Network State")
    }
    Button(onClick = { navController.navigate("darkmode") }) {
        Text("Dark Mode")
        }
    Button(onClick = { navController.navigate("notification") }) {
        Text("Notifications")
    }
    Button(onClick = { navController.navigate("fileupload") }) {
        Text("File Upload")
    }
    Button(onClick = { navController.navigate("crash") }) {
        Text("Crash Test")
        }
    Button(onClick = { navController.navigate("update") }) {
        Text("Update Prompt")
    }
    Button(onClick = { navController.navigate("system") }) {

        Text("System Events")}
    Button(onClick = { navController.navigate("deeplink") }) {
        Text("Deep Link Test")
    }
    Button(onClick = { navController.navigate("playground") }) {
        Text("UI Playground")

    }

    GradientScaffold(
        topBar = { UiTestifyTopBar(title = "UiTestify") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("textfield_email")
            )

            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedItem,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Options") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(dropdownExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .testTag("dropdown_menu")
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    listOf("Option A", "Option B", "Option C").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedItem = option
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checkboxState,
                    onCheckedChange = { checkboxState = it },
                    modifier = Modifier.testTag("checkbox_agree")
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agree to terms")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = switchState,
                    onCheckedChange = { switchState = it },
                    modifier = Modifier.testTag("switch_toggle")
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Enable feature")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Button clicked")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("button_submit")
            ) {
                Text("Show Snackbar")
            }

            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("button_dialog")
            ) {
                Text("Open Dialog")
            }

            Text("Available Test Scenarios:")

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(featureList) { feature ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate(feature.route) }
                            .padding(4.dp)
                            .testTag("feature_card_${feature.route}")
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = feature.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = feature.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirm Action") },
                    text = { Text("Are you sure you want to proceed?") },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}