package com.uitestify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uitestify.navigation.NavGraph
import com.uitestify.ui.theme.UiTestifyTheme
import com.uitestify.ui.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()

            UiTestifyTheme(darkTheme = isDarkMode) {
                Surface(modifier = Modifier) {
                    NavGraph(themeViewModel = themeViewModel)
                }
            }
        }
    }
}
