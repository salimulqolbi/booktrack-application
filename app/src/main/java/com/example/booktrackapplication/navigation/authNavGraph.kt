package com.example.booktrack.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.booktrack.feature.SplashScreen
import com.example.booktrack.feature.login.LoginScreen
import com.example.booktrack.feature.login.RegistrationScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(startDestination = "splash", route = "auth") {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("register") {
            RegistrationScreen(
                navController
            )
        }
        composable("login") {
            LoginScreen(
                navController
            )
        }
    }
}