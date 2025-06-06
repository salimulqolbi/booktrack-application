package com.example.booktrack.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.booktrack.feature.MainScreen
import com.example.booktrack.feature.home.DetailBookScreen
import com.example.booktrack.feature.home.DetailListScreen
import com.example.booktrack.feature.home.FaqScreen
import com.example.booktrack.feature.home.NewsContentScreen
import com.example.booktrack.feature.home.ScheduleList
import com.example.booktrack.feature.profile.AboutScreen
import com.example.booktrack.feature.scan.BookLoanScreen
import com.example.booktrack.feature.scan.ScanScreen
import com.example.booktrackapplication.feature.scan.ReturnLoanScreen
import com.example.booktrackapplication.feature.scan.ReturnScanScreen
import com.example.booktrackapplication.feature.scan.SearchBookScreen
import com.example.booktrackapplication.feature.scan.SearchScanScreen
import com.example.booktrackapplication.viewmodel.MainViewmodel


fun NavGraphBuilder.mainNavGraph(navController: NavController, viewModel: MainViewmodel) {
    navigation(startDestination = "main", route = "main_graph") {
        composable("main") { MainScreen(navController) }
        composable("faq") { FaqScreen(navController) }
        composable("detail_list") { DetailListScreen(navController) }
        composable("schedule_list") { ScheduleList(navController) }
        composable("about_us") { AboutScreen(navController) }
        composable("news_content") {NewsContentScreen(navController)}
        composable("scan_code") {
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

        composable("scan_return_code") {
            ReturnScanScreen(
                navController = navController,
                viewmodel = viewModel,
                onBarcodeScanned = { barcode ->
                    viewModel.fetchReturnBook(barcode)
                }
            )
        }

        composable("return_list_book"){
            ReturnLoanScreen(
                navController,
                viewModel = viewModel
            )
        }

        composable("search_book") {
            SearchScanScreen(
                navController = navController,
                viewModel = viewModel,
                onBarcodeScanned = { barcode ->
                    viewModel.fetchBook(barcode)
                }
            )
        }

        composable("detail_book") {
            SearchBookScreen(
                navController,
                viewmodel = viewModel
            )
        }

        composable("bookDetail") {
            DetailBookScreen(
                navController,
                viewmodel = viewModel
            )
        }
    }
}