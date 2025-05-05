package com.uitestify.ui.screens.playground

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UiPlaygroundScreen(navController: NavController) {
    var counter by remember { mutableStateOf(0) }
    var isExpanded by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(0.5f) }
    var isSwitchOn by remember { mutableStateOf(true) }
    var animatedSize by remember { mutableStateOf(100.dp) }
    var longPressStatus by remember { mutableStateOf("Hold Me") }
    var swipeText by remember { mutableStateOf("Swipe left or right") }
    var doubleTapStatus by remember { mutableStateOf("Double tap here") }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var pinchZoom by remember { mutableStateOf(1f) }

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
            SectionCard("Counter Interaction") {
                Text(
                    "UI State: $counter",
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

            SectionCard("Toggle Feature") {
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

            SectionCard("Slider Control") {
                Text(
                    "Value: ${(sliderValue * 100).toInt()}%",
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

            SectionCard("Expandable Card") {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isExpanded = !isExpanded }
                        .animateContentSize()
                        .testTag("surface_expandable"),
                    shape = RoundedCornerShape(8.dp),
                    tonalElevation = 2.dp
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Tap to ${if (isExpanded) "collapse" else "expand"}")
                        if (isExpanded) {
                            Spacer(Modifier.height(8.dp))
                            Text("Expanded content shown here.", Modifier.testTag("txt_expanded"))
                        }
                    }
                }
            }

            SectionCard("Tap Animation") {
                Box(
                    modifier = Modifier
                        .size(animatedSize)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                        .clickable {
                            animatedSize = if (animatedSize == 100.dp) 160.dp else 100.dp
                        }
                        .animateContentSize()
                        .testTag("animated_box")
                )
                Text("Tap box to grow/shrink", fontSize = 12.sp)
            }

            SectionCard("Long Press Gesture") {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .combinedClickable(
                            onClick = {},
                            onLongClick = { longPressStatus = "✅ Long Pressed" })
                        .testTag("long_press_target"),
                    tonalElevation = 2.dp
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(longPressStatus, Modifier.testTag("txt_long_press_status"))
                    }
                }
            }

            SectionCard("Swipe Detector") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { _, dragAmount ->
                                swipeText =
                                    if (dragAmount > 0) "👉 Swiped Right" else "👈 Swiped Left"
                            }
                        }
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(8.dp)
                        )
                        .testTag("swipe_box"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(swipeText, Modifier.testTag("txt_swipe_status"))
                }
            }

            SectionCard("Double Tap Detection") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onDoubleTap = {
                                doubleTapStatus = "✅ Double Tapped!"
                            })
                        }
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(8.dp)
                        )
                        .testTag("double_tap_box"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(doubleTapStatus, Modifier.testTag("txt_double_tap_status"))
                }
            }

            SectionCard("Drag Gesture") {
                Box(
                    modifier = Modifier
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        }
                        .testTag("box_drag_target"),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Drag", color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            SectionCard("Pinch Zoom Gesture") {
                Box(
                    modifier = Modifier
                        .size((100 * pinchZoom).dp)
                        .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(12.dp))
                        .pointerInput(Unit) {
                            detectTransformGestures { _, _, zoomChange, _ ->
                                pinchZoom *= zoomChange
                            }
                        }
                        .testTag("pinch_zoom_box"),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Pinch Me", color = MaterialTheme.colorScheme.onTertiary)
                }
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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