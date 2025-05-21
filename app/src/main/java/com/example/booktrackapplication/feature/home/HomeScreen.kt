package com.example.booktrackapplication.feature.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.booktrack.utils.HomeCard
import com.example.booktrack.utils.HomeProfileImage
import com.example.booktrack.utils.NewsList
import com.example.booktrack.utils.SearchBar2
import com.example.booktrackapplication.R
import com.example.booktrackapplication.data.NewsData
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.utils.LoanWarningDialog
import com.example.booktrackapplication.utils.ScanBookCard
import com.example.booktrackapplication.viewmodel.MainViewmodel
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

//import org.koin.androidx.compose.get

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = koinViewModel(),
    mainViewmodel: MainViewmodel = koinViewModel()
) {
    val user by viewModel.user.collectAsState()

    var showWarningDialog by remember { mutableStateOf(false) }

    val foundBook by mainViewmodel.searchBook.collectAsStateWithLifecycle()

    val isLoading = user == null

    val searchHistory = mainViewmodel.searchHistory

    LaunchedEffect(isLoading) {
        Log.d("HOME", "User Loaded? ${user != null}")
    }

    val image = R.drawable.anonimus

    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val scannedBook = mainViewmodel.scannedBook

    val searchedBook = mainViewmodel.searchedBook

    BackHandler(enabled = isSearchActive) {
        isSearchActive = false
        focusManager.clearFocus()
    }

    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(scannedBook) {
        if (!hasNavigated && scannedBook != null) {
            hasNavigated = true
            navController.navigate("bookDetail") {
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(Unit) {
        mainViewmodel.reset()
    }

    var searchText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(scannedBook) {
        if (scannedBook != null) {
            navController.navigate("detail_book")
        }
    }

    val state by mainViewmodel.uiState.collectAsState()

//    val history = mainViewmodel.searchHistory

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues())
                .verticalScroll(rememberScrollState())
                .animateContentSize()
        ) {

            //HOME PROFILE
            AnimatedVisibility(visible = !isSearchActive) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        Text(
                            "Selamat Pagi, ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraLight,
                            fontFamily = ManropeFamily,
                            color = Color(0xFF898A89),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = if (user == null) " " else "${user?.name}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    HomeProfileImage(
                        modifier = Modifier.padding(top = 3.dp, bottom = 3.dp),
                        drawableRes = image,
                        description = "Shakira Eveline"
                    )
                }
            }

            ScanBookCard(
                onClick = {
                    navController.navigate("search_book")
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Informasi Terbaru",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = ManropeFamily,
                    color = Color.Black
                )

                Text(
                    "Lihat Jadwal",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    fontFamily = ManropeFamily,
                    color = Color(0xff2846CF),
                    modifier = Modifier
                        .clickable {
                            navController.navigate("schedule_list") {
                                launchSingleTop = true
                            }
                        }
                )
            }

            // HOME CARD
            AnimatedVisibility(visible = !isSearchActive) {
                HomeCard(
                    onClick = {
                        navController.navigate("schedule_list") {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // FAQ SECTION
            AnimatedVisibility(visible = !isSearchActive) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xffEBEBEB), RoundedCornerShape(16.dp))
                            .clickable {
                                navController.navigate("faq")
                            },
                        backgroundColor = Color(0xffF7F8FC)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 20.dp)
                                .height(112.dp)
                                .width(120.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, Color(0xffEBEBEB), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.tabler_icon_help_octagon),
                                    contentDescription = " ",
                                    modifier = Modifier.size(16.dp),
                                    tint = Color(0xff2846CF)
                                )
                            }

                            Column(
                                modifier = Modifier.padding(top = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "FAQ",
                                    color = Color(0xff222222),
                                    fontFamily = ManropeFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                )

                                Text(
                                    "Temukan Jawaban",
                                    color = Color.Gray,
                                    fontSize = 10.sp,
                                    fontFamily = ManropeFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(140.dp)
                            .clickable {
                                showWarningDialog = true
                            }
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color(0xffEBEBEB), RoundedCornerShape(16.dp)),
                        backgroundColor = Color(0xffF7F8FC)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 20.dp)
                                .height(112.dp)
                                .width(120.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, Color(0xffEBEBEB), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_phone),
                                    contentDescription = " ",
                                    modifier = Modifier.size(16.dp),
                                    tint = Color(0xff2846CF)
                                )
                            }

                            Column(
                                modifier = Modifier.padding(top = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Panduan",
                                    color = Color(0xff222222),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = ManropeFamily,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(2.dp)
                                )

                                Text(
                                    "Pengajuan Kehilangan\nBuku",
                                    color = Color.Gray,
                                    fontSize = 10.sp,
                                    fontFamily = ManropeFamily,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                            }

                        }
                    }
                }
            }

            //NEWS SECTION
            AnimatedVisibility(visible = !isSearchActive) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Berita Terbaru",
                        fontSize = 14.sp,
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Lihat Semua",
                        fontSize = 12.sp,
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2846CF),
                        modifier = Modifier.clickable {
                            navController.navigate("detail_list")
                        }
                    )
                }

                NewsList(
                    newsList = NewsData().loadNewsData(),
                    onNewsClick = {
                        navController.navigate("news_content")
                    }
                )
            }

            if(showWarningDialog) {
                LoanWarningDialog(
                    onDismissRequest = { showWarningDialog = false }
                )
            }

            AnimatedVisibility(visible = isSearchActive) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if(searchHistory.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 22.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp)
                                    .clickable {
                                        mainViewmodel.searchBook(searchText)
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Terakhir Dicari",
                                    fontSize = 14.sp,
                                    fontFamily = ManropeFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xff111111)
                                )

                                Text(
                                    "Hapus Semua",
                                    fontSize = 12.sp,
                                    fontFamily = ManropeFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff2846CF),
                                    modifier = Modifier.clickable {
                                        mainViewmodel.clearSearchHistory()
                                    }
                                )
                            }


                            Card(
                                shape = RoundedCornerShape(12.dp),
                                backgroundColor = Color(0XFFF7F8FC),
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    searchHistory.forEach { history ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .padding(bottom = 12.dp)
                                                .clickable {
                                                    searchText = history
                                                    mainViewmodel.searchBook(history)
                                                    mainViewmodel.addToSearchHistory(history)
                                                    navController.navigate("bookDetail")
                                                }
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.tabler_icon_clock),
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )


                                            Spacer(modifier = Modifier.width(6.dp))


                                            Text(
                                                text = history,
                                                fontSize = 12.sp,
                                                fontFamily = ManropeFamily,
                                                fontWeight = FontWeight.SemiBold
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(16.dp)
                                                    .clickable {
                                                        mainViewmodel.removeFromSearchHistory(history)
                                                    }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //FAQ SECTION
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.dp, Color(0xffEBEBEB), RoundedCornerShape(16.dp))
                                .clickable {
                                    navController.navigate("faq")
                                },
                            backgroundColor = Color(0xffF7F8FC)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 12.dp, horizontal = 20.dp)
                                    .height(112.dp)
                                    .width(120.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .border(1.dp, Color(0xffEBEBEB), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.tabler_icon_help_octagon),
                                        contentDescription = " ",
                                        modifier = Modifier.size(16.dp),
                                        tint = Color(0xff2846CF)
                                    )
                                }

                                Column(
                                    modifier = Modifier.padding(top = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "FAQ",
                                        color = Color(0xff222222),
                                        fontFamily = ManropeFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                    )

                                    Text(
                                        "Temukan Jawaban",
                                        color = Color.Gray,
                                        fontSize = 10.sp,
                                        fontFamily = ManropeFamily,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                            }
                        }

                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.dp, Color(0xffEBEBEB), RoundedCornerShape(16.dp)),
                            backgroundColor = Color(0xffF7F8FC)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 12.dp, horizontal = 20.dp)
                                    .height(112.dp)
                                    .width(120.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .border(1.dp, Color(0xffEBEBEB), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_phone),
                                        contentDescription = " ",
                                        modifier = Modifier.size(16.dp),
                                        tint = Color(0xff2846CF)
                                    )
                                }

                                Column(
                                    modifier = Modifier.padding(top = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "Panduan",
                                        color = Color(0xff222222),
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = ManropeFamily,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(2.dp)
                                    )

                                    Text(
                                        "Pengajuan Kehilangan\nBuku",
                                        color = Color.Gray,
                                        fontSize = 10.sp,
                                        fontFamily = ManropeFamily,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        overflow = TextOverflow.Clip
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}