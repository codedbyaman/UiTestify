package com.uitestify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.uitestify.ui.screens.accessibility.AccessibilityScreen
import com.uitestify.ui.screens.async.AsyncFlowScreen
import com.uitestify.ui.screens.crash.CrashTestScreen
import com.uitestify.ui.screens.darkmode.DarkModeScreen
import com.uitestify.ui.screens.deeplink.DeepLinkTestScreen
import com.uitestify.ui.screens.detail.DetailScreen
import com.uitestify.ui.screens.dialogs.DialogsScreen
import com.uitestify.ui.screens.fileupload.FileUploadScreen
import com.uitestify.ui.screens.form.FormValidationScreen
import com.uitestify.ui.screens.home.HomeScreen
import com.uitestify.ui.screens.list.ListSwipeScreen
import com.uitestify.ui.screens.localization.LocalizationScreen
import com.uitestify.ui.screens.login.LoginScreen
import com.uitestify.ui.screens.network.NetworkStateScreen
import com.uitestify.ui.screens.notification.NotificationScreen
import com.uitestify.ui.screens.playground.UiPlaygroundScreen
import com.uitestify.ui.screens.splash.SplashScreen
import com.uitestify.ui.screens.system.SystemEventsScreen
import com.uitestify.ui.screens.update.UpdatePromptScreen
import com.uitestify.ui.viewmodel.ThemeViewModel

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    themeViewModel: ThemeViewModel
) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController, themeViewModel) }
        composable("form") { FormValidationScreen(navController) }
        composable("list") { ListSwipeScreen(navController) }
        composable("dialogs") { DialogsScreen(navController) }
        composable("async") { AsyncFlowScreen(navController) }
        composable("accessibility") { AccessibilityScreen(navController) }
        composable("localization") { LocalizationScreen(navController) }
        composable("network") { NetworkStateScreen(navController) }
        composable("darkmode") { DarkModeScreen(navController) }
        composable("notification") { NotificationScreen(navController) }
        composable("fileupload") { FileUploadScreen(navController) }
        composable("crash") { CrashTestScreen(navController) }
        composable("update") { UpdatePromptScreen(navController) }
        composable("system") { SystemEventsScreen(navController) }
        composable("deeplink") { DeepLinkTestScreen(navController) }
        composable("playground") { UiPlaygroundScreen(navController) }
        composable("login") { LoginScreen(navController) }

        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "uitestify://open/details?id={id}"
                }
            )
        ) {
            val id = it.arguments?.getString("id")
            DetailScreen(id)
        }
    }
}