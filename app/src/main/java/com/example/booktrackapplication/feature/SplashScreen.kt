package com.example.booktrack.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

//import com.example.booktrack.navigation.AuthScreen

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
//        navController.navigate("login") {
//            popUpTo("splash") { inclusive = true }
//        }
        val token = viewModel.getToken()
        if (token.isNullOrEmpty()) {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("main") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Splash Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}