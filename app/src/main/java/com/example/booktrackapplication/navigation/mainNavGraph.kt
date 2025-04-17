package com.example.booktrack.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.booktrack.feature.MainScreen
import com.example.booktrack.feature.home.DetailBookScreen
import com.example.booktrack.feature.home.DetailListScreen
import com.example.booktrack.feature.home.FaqScreen
import com.example.booktrack.feature.home.ScheduleList
import com.example.booktrack.feature.scan.BookLoanScreen
import com.example.booktrack.feature.scan.ScanScreen
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.mainNavGraph(navController: NavController, viewModel: MainViewmodel) {
    navigation(startDestination = "main", route = "main_graph") {
        composable("main") { MainScreen(navController) }
        composable("faq") { FaqScreen(navController) }
        composable("detail_list") { DetailListScreen(navController) }
        composable("schedule_list") { ScheduleList(navController) }
        composable("scan_code") {
//            val viewModel = koinViewModel<MainViewmodel>()
            ScanScreen(
                navController = navController,
                viewModel = viewModel,
                onBarcodeScanned = { barcode ->
                    viewModel.fetchBook(barcode)
                }
            )
        }
        composable("list") {
            BookLoanScreen(
                navController,
                viewModel = viewModel
            )
        }

        composable("bookDetail") {
            DetailBookScreen(navController)
        }


    }
}