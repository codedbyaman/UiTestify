package com.uitestify.ui.screens.localization

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import com.uitestify.R
import com.uitestify.ui.components.UiTestifyTopBar
import com.uitestify.ui.theme.GradientScaffold
import java.util.*

@Composable
fun LocalizationScreen(navController: NavController) {
    val context = LocalContext.current

    fun setLocale(locale: Locale) {val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Localization") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = context.getString(R.string.current_language))

            Button(onClick = { setLocale(Locale.ENGLISH) }) {
                Text(text = context.getString(R.string.switch_to_english))
            }
            Button(onClick = { setLocale(Locale("hi")) }) {
                Text(text = context.getString(R.string.switch_to_hindi))
            }
            Button(onClick = { setLocale(Locale("es")) }) {
                Text(text = context.getString(R.string.switch_to_spanish))
            }
        }
    }
}