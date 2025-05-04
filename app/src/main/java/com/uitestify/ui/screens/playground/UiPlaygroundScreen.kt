package com.uitestify.ui.screens.playground

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UiPlaygroundScreen(navController: NavController) {
    var counter by remember { mutableStateOf(0) }
    var isExpanded by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(0.5f) }
    var isSwitchOn by remember { mutableStateOf(true) }
    var animatedSize by remember { mutableStateOf(100.dp) }
    var longPressed by remember { mutableStateOf(false) }
    var swipeText by remember { mutableStateOf("Swipe left or right") }
    var zoom by remember { mutableFloatStateOf(1f) }

    GradientScaffold(
        topBar = { UiTestifyTopBar("UI Playground") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Counter Section
            SectionCard(title = "Counter Interaction") {
                Text(
                    text = "UI State: $counter",
                    fontSize = 20.sp,
                    modifier = Modifier.testTag("txt_counter")
                )
                Button(
                    onClick = { counter++ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_increment")
                ) {
                    Text("Increase Counter")
                }
            }

            // Switch Section
            SectionCard(title = "Toggle Feature") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enable Feature")
                    Switch(
                        checked = isSwitchOn,
                        onCheckedChange = { isSwitchOn = it },
                        modifier = Modifier.testTag("switch_toggle")
                    )
                }
            }

            // Slider Section
            SectionCard(title = "Slider Control") {
                Text(
                    text = "Value: ${(sliderValue * 100).toInt()}%",
                    modifier = Modifier.testTag("txt_slider_val")
                )
                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("slider")
                )
            }

            // Expandable Section
            SectionCard(title = "Expandable Card") {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded }
                        .animateContentSize()
                        .testTag("surface_expandable"),
                    shape = RoundedCornerShape(8.dp),
                    tonalElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Tap to ${if (isExpanded) "collapse" else "expand"}")
                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Expanded content shown here.",
                                modifier = Modifier.testTag("txt_expanded")
                            )
                        }
                    }
                }
            }

            // Animated Box on Tap
            SectionCard(title = "Tap Animation") {
                Box(
                    modifier = Modifier
                        .size(animatedSize)
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp))
                        .clickable {
                            animatedSize = if (animatedSize == 100.dp) 160.dp else 100.dp
                        }
                        .animateContentSize()
                        .testTag("animated_box")
                )
                Text("Tap box to grow/shrink", fontSize = 12.sp)
            }

            // Long Press Gesture
            SectionCard(title = "Long Press Gesture") {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .combinedClickable(
                            onClick = {},
                            onLongClick = { longPressed = !longPressed }
                        )
                        .testTag("long_press_target"),
                    tonalElevation = 2.dp
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            if (longPressed) "✅ Long Pressed" else "Hold Me",
                            modifier = Modifier.testTag("txt_long_press_status")
                        )
                    }
                }
            }

            // Horizontal Swipe Detection
            SectionCard(title = "Swipe Detector") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                swipeText = if (dragAmount > 0) "👉 Swiped Right" else "👈 Swiped Left"
                            }
                        }
                        .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
                        .testTag("swipe_box"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(swipeText, modifier = Modifier.testTag("txt_swipe_status"))
                }
            }

            // Zoom In/Out Box
            SectionCard(title = "Zoom (Scale)") {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { zoom *= 1.2f }, modifier = Modifier.testTag("btn_zoom_in")) {
                        Text("Zoom In")
                    }
                    Button(onClick = { zoom *= 0.8f }, modifier = Modifier.testTag("btn_zoom_out")) {
                        Text("Zoom Out")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .size((100 * zoom).dp)
                        .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(10.dp))
                        .testTag("box_zoom_target")
                )
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            content()
        }
    }
}