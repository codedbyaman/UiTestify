package com.uitestify.ui.screens.localization

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
    var selectedLocale by remember { mutableStateOf(Locale.getDefault()) }

    fun setLocale(locale: Locale) {
        val appLocale = LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        AppCompatDelegate.setApplicationLocales(appLocale)
        selectedLocale = locale
    }

    GradientScaffold(
        topBar = { UiTestifyTopBar("Localization") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.current_language),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .testTag("txt_current_language")
                    .semantics { contentDescription = "Current Language" }
            )

            Text(
                text = "🌍 ${selectedLocale.displayLanguage} (${selectedLocale.language})",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .semantics { contentDescription = "Selected language is ${selectedLocale.displayLanguage}" }
                    .testTag("txt_selected_language")
            )

            Button(
                onClick = { setLocale(Locale.ENGLISH) },
                modifier = Modifier.testTag("btn_english")
            ) {
                Text(text = context.getString(R.string.switch_to_english))
            }

            Button(
                onClick = { setLocale(Locale("hi")) },
                modifier = Modifier.testTag("btn_hindi")
            ) {
                Text(text = context.getString(R.string.switch_to_hindi))
            }

            Button(
                onClick = { setLocale(Locale("es")) },
                modifier = Modifier.testTag("btn_spanish")
            ) {
                Text(text = context.getString(R.string.switch_to_spanish))
            }
        }
    }
}