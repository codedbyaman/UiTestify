package com.uitestify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uitestify.ui.screens.async.AsyncFlowScreen
import com.uitestify.ui.screens.dialogs.DialogsScreen
import com.uitestify.ui.screens.form.FormValidationScreen
import com.uitestify.ui.screens.home.HomeScreen
import com.uitestify.ui.screens.list.ListSwipeScreen
import com.uitestify.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("form") { FormValidationScreen(navController) }
        composable("list") { ListSwipeScreen(navController) }
        composable("dialogs") { DialogsScreen(navController) }
        composable("async") {
            AsyncFlowScreen(navController)
        }


    }
}
