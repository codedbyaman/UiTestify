package com.uitestify.ui.screens.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListSwipeScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var items by remember { mutableStateOf((1..10).map { "Swipe Item $it" }.toMutableList()) }

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

                val dismissState = rememberDismissState(
                    confirmStateChange = { dismissValue ->
                        when (dismissValue) {
                            DismissValue.DismissedToStart -> {
                                handleDelete(
                                    item = item,
                                    index = index,
                                    items = items,
                                    setItems = { items = it as MutableList<String> },
                                    snackbarHostState = snackbarHostState,
                                    coroutineScope = coroutineScope,
                                    restoreItem = { idx, value ->
                                        items = items.toMutableList().apply {
                                            add(idx.coerceIn(0, size), value)
                                        }
                                    }
                                )
                                true
                            }

                            DismissValue.DismissedToEnd -> {
                                coroutineScope.launch {
                                    items = items.toMutableList().apply { removeAt(index) }
                                    snackbarHostState.showSnackbar("$item archived")
                                }
                                true
                            }

                            else -> false
                        }
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(StartToEnd, EndToStart),
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            StartToEnd -> Color(0xFF64B5F6) // Blue for archive
                            EndToStart -> Color(0xFFEF5350) // Red for delete
                            else -> Color.LightGray
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color),
                            contentAlignment = Alignment.Center
                        ) {
                            val text = when (dismissState.dismissDirection) {
                                StartToEnd -> "Archiving..."
                                EndToStart -> "Deleting..."
                                else -> ""
                            }
                            Text(text = text, color = Color.White)
                        }
                    },
                    dismissContent = {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clipToBounds()
                                .testTag("swipe_item_$index"),
                            elevation = 4.dp
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

private fun handleDelete(
    item: String,
    index: Int,
    items: List<String>,
    setItems: (List<String>) -> Unit,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    restoreItem: (Int, String) -> Unit
) {
    coroutineScope.launch {
        setItems(items.toMutableList().apply { removeAt(index) })

        val result = snackbarHostState.showSnackbar(
            message = "$item deleted",
            actionLabel = "Undo"
        )

        if (result == SnackbarResult.ActionPerformed) {
            restoreItem(index, item)
        }
    }
}