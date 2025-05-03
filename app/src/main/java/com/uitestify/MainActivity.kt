package com.uitestify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.uitestify.navigation.NavGraph
import com.uitestify.ui.theme.UiTestifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiTestifyTheme {
                Surface(modifier = Modifier) {
                    NavGraph()
                }
            }
        }
    }
}