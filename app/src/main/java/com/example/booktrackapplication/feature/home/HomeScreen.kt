package com.example.booktrackapplication.feature.home

//import androidx.compose.material.ButtonDefaults
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.navigation.compose.rememberNavController
import com.example.booktrack.utils.HomeProfileImage
import com.example.booktrack.utils.NewsList
import com.example.booktrack.utils.SearchBar2
import com.example.booktrackapplication.R
import com.example.booktrackapplication.data.NewsData
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.viewmodel.MainViewmodel
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

//import org.koin.androidx.compose.get

val searchHistory = listOf(
    "BA - 7862 - SD76",
    "CY - 9711 - BH73",
    "MK - 1788 - 2222"
)

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = koinViewModel()
) {

    val mainViewmodel: MainViewmodel = koinViewModel()

    val user by viewModel.user.collectAsStateWithLifecycle()

    val isLoading = user == null
    var isLoading2 by remember { mutableStateOf(false) }
    val name = user?.name ?: " "

    LaunchedEffect(isLoading) {
        Log.d("HOME", "User Loaded? ${user != null}")
    }

    Log.d("HOME", "Render HomeScreen. User: ${user?.name}")

    val image = R.drawable.anonimus

    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    BackHandler(enabled = isSearchActive) {
        isSearchActive = false
        focusManager.clearFocus()
    }

    if (isLoading) {
        // Kasih shimmer, loading, atau sekadar Spacer
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
                .animateContentSize()
        ) {

            //HOME PROFILE
            AnimatedVisibility(visible = !isSearchActive) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
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

            //SEARCH BAR
            Row(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
//                    bottom = 16.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
//                        .height(48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                var active by rememberSaveable { mutableStateOf(false) }
                var searchText by rememberSaveable { mutableStateOf("") }

                val searchBarOffset by animateDpAsState(
                    targetValue = if (isSearchActive) 0.dp else 20.dp,
                    label = "searchBarOffset"
                )

                SearchBar2(
                    placeholderText = "Apa yang ingin anda cari?",
                    query = searchText,
                    onQueryChange = { searchText = it },
                    onSearch = { /* Dummy search action */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xffEBEBEB), RoundedCornerShape(8.dp))
                        .background(Color(0xffF7F8FC))
                        .height(52.dp),
                    active = isSearchActive,
                    onActiveChange = { isSearchActive = it }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_scan_filled),
                    contentDescription = "Scan",
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(52.dp)
                )

            }

            // HOME CARD
            AnimatedVisibility(visible = !isSearchActive) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 20.dp, end = 20.dp, top = 16.dp)
//                ) {
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(10.dp))
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .wrapContentHeight() // <--- biar tinggi ngikutin isi
//                                .background(Color.Red) // untuk debug tinggi column
//                        ) {
//                            Image(
//                                painter = painterResource(R.drawable.background),
//                                contentDescription = "Card Event",
//                                modifier = Modifier.fillMaxWidth()
//                            )
//
//                            // Konten teks dan tombol
//                            Column(modifier = Modifier.padding(bottom = 12.dp)) {
//                                Row(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(start = 16.dp, top = 12.dp, end = 16.dp),
//                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Card(
//                                        shape = RoundedCornerShape(32),
//                                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
//                                        backgroundColor = Color.White.copy(alpha = 0.1f),
//                                        modifier = Modifier
//                                            .clickable {}
//                                            .height(24.dp)
//                                    ) {
//                                        Text(
//                                            text = "PENGUMUMAN \uD83D\uDCDD",
//                                            fontSize = 10.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            fontFamily = ManropeFamily,
//                                            color = Color.White.copy(alpha = 0.5f),
//                                            modifier = Modifier.padding(horizontal = 12.dp)
//                                        )
//                                    }
//
//                                    Icon(
//                                        imageVector = Icons.Filled.MoreHoriz,
//                                        contentDescription = " ",
//                                        tint = Color.White
//                                    )
//                                }
//
//                                Text(
//                                    text = "Jadwal Pengambilan dan\nPengembalian",
//                                    fontWeight = FontWeight.Bold,
//                                    modifier = Modifier.padding(top = 8.dp, start = 16.dp),
//                                    fontSize = 14.sp,
//                                    fontFamily = ManropeFamily,
//                                    color = Color.White,
//                                    lineHeight = 20.sp
//                                )
//
//                                Text(
//                                    text = "Buku mata pelajaran ditujukan\nkepada seluruh siswa dan siswi\nSMKN 7 Semarang.",
//                                    modifier = Modifier.padding(top = 6.dp, start = 16.dp),
//                                    fontWeight = FontWeight.Light,
//                                    fontFamily = ManropeFamily,
//                                    fontSize = 10.sp,
//                                    color = Color.White,
//                                    lineHeight = 16.sp
//                                )
//
//                                Button(
//                                    onClick = { navController.navigate("schedule_list") },
//                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2846CF)),
//                                    modifier = Modifier
//                                        .padding(start = 16.dp, top = 12.dp)
//                                        .height(36.dp)
//                                        .width(140.dp),
//                                    shape = RoundedCornerShape(30.dp)
//                                ) {
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = stringResource(id = R.string.lihat),
//                                            fontFamily = ManropeFamily,
//                                            fontWeight = FontWeight.Medium,
//                                            fontSize = 12.sp,
//                                            color = Color.White
//                                        )
//
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.tabler_icon_arrow_narrow_right),
//                                            contentDescription = "See",
//                                            tint = Color.White,
//                                            modifier = Modifier.size(14.dp)
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .paint(
                            painter = painterResource(R.drawable.background),
                            contentScale = ContentScale.FillWidth
                        )

                ) {

                    Column(modifier = Modifier.wrapContentHeight()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 12.dp, end = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                shape = RoundedCornerShape(32),
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                                backgroundColor = Color.White.copy(alpha = 0.1f),
                                modifier = Modifier
                                    .clickable { }
                                    .height(24.dp)
                            ) {
                                Text(
                                    text = "PENGUMUMAN \uD83D\uDCDD",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = ManropeFamily,
                                    color = Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                    )
                                )
                            }

                            Icon(
                                imageVector = Icons.Filled.MoreHoriz,
                                contentDescription = " ",
                                tint = Color.White
                            )
                        }

                        Text(
                            text = "Jadwal Pengambilan dan\nPengembalian",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                            fontSize = 14.sp,
                            fontFamily = ManropeFamily,
                            color = Color.White,
                            lineHeight = 20.sp
                        )

                        Text(
                            text = "Buku mata pelajaran ditujukan\nkepada seluruh siswa dan siswi\nSMKN 7 Semarang.",
                            modifier = Modifier.padding(top = 6.dp, start = 16.dp),
                            fontWeight = FontWeight.Light,
                            fontFamily = ManropeFamily,
                            fontSize = 10.sp,
                            color = Color.White,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
//                                navController.navigate("schedule_list")
                                isLoading2 = true
                                mainViewmodel.getSchedule {
                                    isLoading2 = false
                                    navController.navigate("schedule_list")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff2846CF)
                            ),
                            modifier = Modifier
                                .padding(start = 16.dp, bottom = 8.dp)
                                .height(36.dp)
                                .width(140.dp),
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.lihat),
                                    fontFamily = ManropeFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.tabler_icon_arrow_narrow_right),
                                    contentDescription = "See",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(10.dp))
//                    ) {
////                        Image(
////                            painter = painterResource(R.drawable.background),
////                            contentDescription = "Card Event",
////                            modifier = Modifier.fillMaxWidth()
////                        )
//
//
//                    }
                }
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
                    newsList = NewsData().loadNewsData()
                )
            }

            AnimatedVisibility(visible = isSearchActive) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
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
                                color = Color(0xff2846CF)
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
                                            .clickable { }
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
                                                .clickable { }
                                        )
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

@Preview(showBackground = true)
@Composable
fun HomePreview(
) {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}