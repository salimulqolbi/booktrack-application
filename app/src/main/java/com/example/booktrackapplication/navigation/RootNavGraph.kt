package com.example.booktrack.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.booktrackapplication.feature.home.HomeScreen
import com.example.booktrackapplication.model.NavItems

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.AUTH,
        route = Graph.ROOT
    ) {
        authNavGraph(navController)

        navigation(
            route = Graph.MAIN,
            startDestination = NavItems.Home.route
        ) {
            composable(route = NavItems.Home.route) {
                HomeScreen(navController)
            }
        }
    }
}

object Graph {
    const val ROOT = "root"
    const val AUTH = "auth"
    const val MAIN = "main_screen"
}