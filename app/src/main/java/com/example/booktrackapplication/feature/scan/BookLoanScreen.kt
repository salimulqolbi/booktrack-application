package com.example.booktrack.feature.scan

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.booktrack.data.response.BookData
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookLoanScreen(
    navController: NavController,
    viewModel: MainViewmodel = koinViewModel()
) {
    val books = viewModel.loanedBooks

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 40.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(Color(0XFFF7F8FC)),
                border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color(0xff2846CF),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Text(
                text = "Peminjaman Buku",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(Color.Transparent),
                modifier = Modifier.size(44.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = Color.Transparent,
                        modifier = Modifier.size(24.dp),

                        )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 130.dp)
            ) {
                items(books) { book ->
                    BookItem(
                        book = book,
                        onDeleteClick = {
                            viewModel.removeBook(book.code)
                        }
                    )
                }

                item {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2846CF))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.submit_buku),
                                fontSize = 12.sp,
                                color = Color(0xffFFFFFF),
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.SemiBold
                            )

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = null,
                                tint = Color(0xffFFFFFF),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .padding(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 24.dp),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xff2846CF).copy(
                                alpha = 0.1f
                            )
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${books.size}/12",
                                fontSize = 12.sp,
                                color = Color(0xff111111).copy(0.6f),
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(book: BookData, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 12.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(60.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF7F8FC))
                .border(1.dp, Color(0xFFEBEBEB).copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = book.coverUrl,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp)),
                onError = {
                    Log.e("AsyncImage", "Gagal load gambar: ${it.result.throwable}")
                }
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = book.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ManropeFamily
            )

            Card(
                shape = RoundedCornerShape(80),
                border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                colors = CardDefaults.cardColors(Color(0xffF7F8FC)),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .height(22.dp)
            ) {
                Text(
                    text = book.code,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    fontSize = 10.sp,
                    color = Color.Black,
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.delete_ic),
            contentDescription = "Delete",
            tint = Color(0xFFE57373),
            modifier = Modifier
                .size(16.dp)
                .clickable { onDeleteClick() }
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Divider(modifier = Modifier.padding(horizontal = 20.dp))
}