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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        var email by remember { mutableStateOf("") }
        var dropdownExpanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf("Select Option") }
        var checkboxState by remember { mutableStateOf(false) }
        var switchState by remember { mutableStateOf(true) }
        var showDialog by remember { mutableStateOf(false) }

        val listItems = (1..10).map { "Item $it" }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(title = { Text("UI Test Playground") })
            }
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

                Text("List of Items:")

                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.4f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(listItems) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* simulate click */ }
                                .padding(4.dp)
                                .testTag("list_item_$item")
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.padding(16.dp)
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