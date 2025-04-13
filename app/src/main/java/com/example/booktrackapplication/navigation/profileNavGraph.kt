package com.example.booktrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.booktrack.feature.profile.EditProfileScreen
import com.example.booktrack.feature.profile.ProfileScreen

//@Composable
//fun NavGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        startDestination = "profile"
//    ) {
//        composable("profile") {
//            ProfileScreen(navController)
//        }
//        composable("edit_profile/{userName}") { backStackEntry ->
//            EditProfileScreen(navController)
//        }
//    }
//}

