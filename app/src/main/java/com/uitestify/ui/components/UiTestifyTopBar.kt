package com.uitestify.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uitestify.ui.theme.BackgroundGradient
import com.uitestify.ui.theme.TopBackgroundGradient

@Composable
fun UiTestifyTopBar(title: String) {
    Surface(
        tonalElevation = 4.dp
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(TopBackgroundGradient))
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

