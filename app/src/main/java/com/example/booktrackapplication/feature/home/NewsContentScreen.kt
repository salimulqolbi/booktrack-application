package com.example.booktrack.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily

@Composable
fun NewsContentScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 52.dp, start = 20.dp, end = 20.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xffF7F8FC))
                        .border(1.dp, Color(0xffEBEBEB), CircleShape)
                        .align(Alignment.CenterStart)
                ) {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = "Back",
                        tint = Color(0xff2846CF),
                    )
                }

                Text(
                    " ",
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(paddingValues),
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.book_content_1),
                        contentDescription = "News Content 1",
                        modifier = Modifier
                            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                    )
                }

                item {
                    Text(
                        text = "Digitalisasi Pengambilan Buku Mapel Stemba",
                        fontSize = 16.sp,
                        fontFamily = ManropeFamily,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
                            .widthIn(max = 264.dp)
                    )
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    ) {
                        Text(
                            text = "20 April 2025",
                            fontSize = 12.sp,
                            fontFamily = ManropeFamily,
                            color = Color(0xFF777777),
                            fontWeight = FontWeight.Medium,
                        )

                        Icon(
                            imageVector = Icons.Filled.Circle,
                            contentDescription = " ",
                            modifier = Modifier.size(6.dp),
                            tint = Color(0xFFD9D9D9)
                        )

                        Text(
                            text = "09:28 WIB",
                            fontSize = 12.sp,
                            fontFamily = ManropeFamily,
                            color = Color(0xFF777777),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.content_book1),
                            style = TextStyle(textAlign = TextAlign.Justify),
                            textAlign = TextAlign.Justify,
                            fontSize = 14.sp,
                            fontFamily = ManropeFamily,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .background(Color(0xffF7F8FC))
                    ) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(130.dp)
                                .background(Color(0xff2846CF))
                        )

                        Text(
                            text = "Beralih ke sistem digital menghadirkan kemudahan dan efisiensi, mengubah cara belajar menjadi lebih praktis dan terstruktur.",
                            fontSize = 16.sp,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff586058),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, top = 10.dp, end = 8.dp, bottom = 10.dp)
                        )
                    }
                }

                item {
                    Text(
                        text = stringResource(id = R.string.content_book1_1),
                        textAlign = TextAlign.Justify,
                        fontSize = 14.sp,
                        fontFamily = ManropeFamily,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.content_book1_2),
                        textAlign = TextAlign.Justify,
                        fontSize = 14.sp,
                        fontFamily = ManropeFamily,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    )
                }
            }
        }
    )
}