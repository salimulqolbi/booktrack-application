package com.example.booktrack.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppNavigation() {
    val sharedViewModel: MainViewmodel = koinViewModel()
    val navController = rememberNavController()
    NavHost(navController, startDestination = "auth") {
        authNavGraph(navController)
        mainNavGraph(navController, sharedViewModel)
    }
}