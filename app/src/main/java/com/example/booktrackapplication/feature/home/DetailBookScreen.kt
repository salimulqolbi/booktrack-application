package com.example.booktrack.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.utils.convertToRoman
import com.example.booktrackapplication.utils.extractAbbreviation
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailBookScreen(
    navController: NavController,
    viewmodel: MainViewmodel = koinViewModel()
) {

    val book = viewmodel.scannedBook

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(top = 24.dp),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("main") {
                            launchSingleTop = true
                        }
                        viewmodel.reset()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xffF7F8FC))
                        .border(1.dp, Color(0xffEBEBEB), CircleShape)
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = "Back",
                        tint = Color(0xff2846CF),
                    )
                }

                Text(
                    "Detail Buku",
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                book?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = book.coverUrl,
                            contentDescription = book.title,
                            modifier = Modifier
                                .width(100.dp)
                                .height(144.dp)
                                .padding(bottom = 12.dp),
                        )
                        Text(
                            text = book.title,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                        )
                        Text(
                            text = "Kelas ${book.grade}",
                            fontFamily = ManropeFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black.copy(alpha = 0.5f),
                            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .width(335.dp)
                            .height(300.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, Color(0xffEBEBEB), RoundedCornerShape(16.dp)),
                        backgroundColor = Color(0xffF7F8FC)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 7.dp)
                            ) {
                                Text(
                                    text = "Kode Buku",
                                    fontFamily = ManropeFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )

                                Card(
                                    shape = RoundedCornerShape(80),
                                    border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                                    backgroundColor = Color(0xffFFFFFF),
                                    modifier = Modifier
                                ) {
                                    Text(
                                        book.code,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp),
                                        fontSize = 10.sp,
                                        color = Color.Black,
                                        fontFamily = ManropeFamily,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Divider(
                                color = Color(0xffEBEBEB),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 7.dp)
                            ) {
                                Text(
                                    text = "Jenis Buku",
                                    fontFamily = ManropeFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )

                                Card(
                                    shape = RoundedCornerShape(80),
                                    border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                                    backgroundColor = Color(0xffFFFFFF),
                                    modifier = Modifier
                                ) {
                                    Text(
                                        book.bookType,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp),
                                        fontSize = 10.sp,
                                        color = Color.Black,
                                        fontFamily = ManropeFamily,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Divider(
                                color = Color(0xffEBEBEB),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 7.dp)
                            ) {
                                Text(
                                    text = "Semester",
                                    fontFamily = ManropeFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )

                                Card(
                                    shape = RoundedCornerShape(80),
                                    border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                                    backgroundColor = Color(0xffFFFFFF),
                                    modifier = Modifier
                                ) {
                                    Text(
                                        "Semester ${book.semesters.replace(",", " & ")}",
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp),
                                        fontSize = 10.sp,
                                        color = Color.Black,
                                        fontFamily = ManropeFamily,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Divider(
                                color = Color(0xffEBEBEB),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            Column {
                                Text(
                                    "Pemegang Buku Saat Ini",
                                    fontFamily = ManropeFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 12.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, Color(0xffEBEBEB))
                                ) {
                                    Column {
                                        // Row 1
                                        Row(
                                            modifier = Modifier
                                        ) {
                                            // Column 1
                                            Box(
                                                modifier = Modifier
                                                    .drawBehind {
                                                        drawIntoCanvas { canvas ->
                                                            val paint = Paint().apply {
                                                                color = Color(0xffEBEBEB)
                                                                strokeWidth = 1.dp.toPx()
                                                            }
                                                            val endY = size.height
                                                            canvas.drawLine(
                                                                p1 = Offset(x = size.width, y = 0f),
                                                                p2 = Offset(x = size.width, y = endY),
                                                                paint = paint
                                                            )
                                                        }
                                                    }
                                                    .width(80.dp)
                                                    .padding(top = 4.dp, bottom = 4.dp, start = 10.dp),
                                            ) {
                                                Text(
                                                    text = "Nama Siswa",
                                                    fontSize = 10.sp,
                                                    fontFamily = ManropeFamily,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color.Black.copy(alpha = 0.6f)
                                                )
                                            }
                                            // Column 2
                                            Box(
                                                modifier = Modifier
                                                    .padding(start = 12.dp, top = 4.dp, bottom = 4.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = book.borrowedBy?.name ?: "-",
                                                    fontSize = 10.sp,
                                                    fontFamily = ManropeFamily,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color(0xff333333)
                                                )

                                            }
                                        }

                                        // Row 2
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(BorderStroke(1.dp, Color(0xffEBEBEB)))
                                        ) {
                                            // Column 1
                                            Box(
                                                modifier = Modifier
                                                    .drawBehind {
                                                        drawIntoCanvas { canvas ->
                                                            val paint = Paint().apply {
                                                                color = Color(0xffEBEBEB)
                                                                strokeWidth = 1.dp.toPx()
                                                            }
                                                            val endY = size.height
                                                            canvas.drawLine(
                                                                p1 = Offset(x = size.width, y = 0f),
                                                                p2 = Offset(x = size.width, y = endY),
                                                                paint = paint
                                                            )
                                                        }
                                                    }
                                                    .width(80.dp),
                                            ) {
                                                Text(
                                                    text = "Kelas",
                                                    modifier = Modifier
                                                        .padding(
                                                            top = 4.dp,
                                                            bottom = 4.dp,
                                                            start = 10.dp
                                                        ),
                                                    fontSize = 10.sp,
                                                    fontFamily = ManropeFamily,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color.Black.copy(alpha = 0.6f)
                                                )
                                            }
                                            // Column 2

                                            val borrowerInfo = if (book.borrowedBy?.classIndex != null) {
                                                "${convertToRoman(book.grade)} ${extractAbbreviation(book.department)} ${book.borrowedBy.classIndex}"
                                            } else {
                                                "-"
                                            }
                                            Box(
                                                modifier = Modifier,
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = borrowerInfo,
                                                    fontSize = 10.sp,
                                                    fontFamily = ManropeFamily,
                                                    modifier = Modifier.padding(
                                                        start = 12.dp,
                                                        top = 4.dp,
                                                        bottom = 4.dp
                                                    ),
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color(0xff333333)
                                                )
                                            }
                                        }

                                        // Row 3
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .drawBehind {
                                                        drawIntoCanvas { canvas ->
                                                            val paint = Paint().apply {
                                                                color = Color(0xffEBEBEB)
                                                                strokeWidth = 1.dp.toPx()
                                                            }
                                                            val endY = size.height
                                                            canvas.drawLine(
                                                                p1 = Offset(x = size.width, y = 0f),
                                                                p2 = Offset(x = size.width, y = endY),
                                                                paint = paint
                                                            )
                                                        }
                                                    }
                                                    .width(80.dp),
                                            ) {
                                                Text(
                                                    text = "No. Wa",
                                                    modifier = Modifier
                                                        .padding(
                                                            top = 4.dp,
                                                            bottom = 4.dp,
                                                            start = 10.dp
                                                        ),
                                                    fontSize = 10.sp,
                                                    fontFamily = ManropeFamily,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color.Black.copy(alpha = 0.6f)
                                                )
                                            }
                                            // Column 2
                                            Box(
                                                modifier = Modifier,
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = book?.borrowedBy?.phoneNumber ?: "-",
                                                    fontSize = 10.sp,
                                                    modifier = Modifier.padding(
                                                        start = 12.dp,
                                                        top = 4.dp,
                                                        bottom = 4.dp
                                                    ),
                                                    fontFamily = ManropeFamily,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color(0xff333333)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(bottom = 16.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = stringResource(id = R.string.footer1),
                            fontFamily = ManropeFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                        Text(
                            text = stringResource(id = R.string.footer2),
                            fontFamily = ManropeFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                } ?: Text("Buku tidak ditemukan")
            }
        }
    )
}