package com.example.booktrack.feature

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booktrackapplication.R
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val logoScale = remember { Animatable(0f) }
    val logoOffsetX = remember { Animatable(0f) }
    val logoRotation = remember { Animatable(0f) }
    val textOffsetX = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()
    val userState = viewModel.userUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    LaunchedEffect(userState) {
        logoScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )

        // Step 2: Mulai animasi logo dan teks secara bersamaan
        scope.launch {
            // Logo geser kiri
            logoOffsetX.animateTo(
                targetValue = -150f,
                animationSpec = tween(700)
            )
        }

        scope.launch {
            // Logo rotasi ke kiri
            logoRotation.animateTo(
                targetValue = -360f,
                animationSpec = tween(700)
            )
        }

        scope.launch {
            // Text muncul langsung dan geser kanan
            textAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(700)
            )
        }

        scope.launch {
            textOffsetX.animateTo(
                targetValue = 150f,
                animationSpec = tween(700)
            )
        }

        delay(1000L)

//        val token = viewModel.getToken()
//        if (token.isNullOrEmpty()) {
//            navController.navigate("login") {
//                popUpTo("splash") { inclusive = true }
//            }
//        } else {
//            viewModel.getUser()
//            navController.navigate("main") {
//                popUpTo("splash") { inclusive = true }
//            }
//        }

        if (!userState.value.isLoading) {
            if (userState.value.user != null) {
                navController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                }
            } else if (userState.value.errorMessage != null) {
                viewModel.clearToken() // Hapus token saat gagal
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

//    LaunchedEffect(Unit) {
////        navController.navigate("login") {
////            popUpTo("splash") { inclusive = true }
////        }
//
//    }

//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text(text = "Splash Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.biblioo_logo),
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = logoScale.value
                        scaleY = logoScale.value
                        translationX = logoOffsetX.value
                        rotationZ = logoRotation.value
                    }
                    .size(64.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            // Text BookTrack
            Text(
                text = "Book Track",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = textOffsetX.value
                        alpha = textAlpha.value
                    }
                    .alpha(if (textOffsetX.value > 0f) 1f else 0f)
            )
        }
    }
}