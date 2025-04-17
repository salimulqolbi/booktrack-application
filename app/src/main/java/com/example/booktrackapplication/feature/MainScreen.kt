package com.example.booktrack.feature

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.booktrack.feature.history.HistoryScreen
import com.example.booktrack.feature.profile.ProfileScreen
import com.example.booktrackapplication.R
import com.example.booktrackapplication.feature.activity.ActivityScreen
import com.example.booktrackapplication.feature.home.HomeScreen
import com.example.booktrackapplication.model.NavItems
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.utils.ScanWarningDialog
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navController: NavController) {
    val bottomNavController = rememberNavController()

    var showWarningDialog by remember { mutableStateOf(false) }

    val viewModel: MainViewmodel = koinViewModel()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showWarningDialog = true },
                backgroundColor = Color.Black,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_scan_qr),
                    contentDescription = "Scan QR",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        bottomBar = {
            BottomNavigationBar(bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("activity") { ActivityScreen() }
            composable("history") { HistoryScreen() }
            composable("profile") {
                ProfileScreen(navController)
            }
        }
    }

    if (showWarningDialog) {
        ScanWarningDialog(
            onDismissRequest = { showWarningDialog = false },
            navController
        )
    }
}

@Composable
fun BottomNavigationBar(bottomNavController: NavController) {
    val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route

    val leftItems = listOf(NavItems.Home, NavItems.Activity)
    val rightItems = listOf(NavItems.History, NavItems.Profile)

    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(
                bottom = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
            .height(68.dp)
    ) {
        // Items di kiri (Home & Aktivitas)
        Row(
            modifier = Modifier
                .weight(2f)
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leftItems.forEach { item ->
                BottomNavItem(bottomNavController, item, currentRoute)
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Memberi ruang untuk FAB

        // Items di kanan (Riwayat & Profil)
        Row(
            modifier = Modifier
                .weight(2f)
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            rightItems.forEach { item ->
                BottomNavItem(bottomNavController, item, currentRoute)
            }
        }
    }
}

@Composable
fun BottomNavItem(navController: NavController, item: NavItems, currentRoute: String?) {
    val isSelected = currentRoute == item.route
    Log.d(
        "BottomNavItem",
        "Route: ${item.route}, currentRoute: $currentRoute, isSelected: $isSelected"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            .width(52.dp)

    ) {
        Image(
            painter = painterResource(id = if (isSelected) item.selectedIcon else item.unselectedIcon),
            contentDescription = item.title,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.title,
            fontSize = 12.sp,
            fontFamily = ManropeFamily,
            fontWeight = FontWeight.Normal
        )
    }
}
