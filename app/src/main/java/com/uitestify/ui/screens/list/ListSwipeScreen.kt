package com.uitestify.ui.screens.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListSwipeScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var items by remember { mutableStateOf((1..10).map { "Swipe Item $it" }.toMutableList()) }
    var recentlyDeleted: Pair<Int, String>? by remember { mutableStateOf(null) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("List Swipe") },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(items, key = { _, item -> item }) { index, item ->
                val dismissState = rememberDismissState()

                // ✅ Only trigger once when dismissed
                if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                    dismissState.isDismissed(DismissDirection.StartToEnd)
                ) {
                    LaunchedEffect(key1 = item) {
                        if (items.contains(item)) {
                            recentlyDeleted = index to item
                            items = items.toMutableList().apply { removeAt(index) }

                            val result = snackbarHostState.showSnackbar(
                                message = "$item removed",
                                actionLabel = "Undo"
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                recentlyDeleted?.let { (restoreIndex, value) ->
                                    items = items.toMutableList().apply {
                                        add(restoreIndex.coerceIn(0, size), value)
                                    }
                                    recentlyDeleted = null
                                }
                            }
                        }
                    }
                }

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(
                        DismissDirection.StartToEnd,
                        DismissDirection.EndToStart
                    ),
                    background = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.error)
                        )
                    },
                    dismissContent = {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clipToBounds()
                                .testTag("swipe_item_$index")
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        Log.d("ListSwipeScreen", "Screen opened")
    }
}
