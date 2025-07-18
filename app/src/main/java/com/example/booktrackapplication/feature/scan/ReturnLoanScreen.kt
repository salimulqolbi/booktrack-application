package com.example.booktrackapplication.feature.scan

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.booktrack.data.response.BookData
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReturnLoanScreen(
    navController: NavController,
    viewModel: MainViewmodel = koinViewModel()
) {
    val books = viewModel.returnLoanedBooks

    val returnUiState by viewModel.returnUiState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val bookCount = uiState.curriculum?.bookCount ?: " "
    Log.d("book", "jumlah max buku: $bookCount")

    Log.d("book", "curriculum: ${uiState.curriculum}")

    LaunchedEffect(Unit) {
        viewModel.clearSubmitMessage()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(top = 24.dp),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                IconButton(
                    onClick = {
                        viewModel.resetReturnBook()
                        navController.navigate("main") {
                            popUpTo("return_list_book") { inclusive = true }
                            launchSingleTop = true
                        }

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
                    "Pengembalian Buku",
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
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
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
                                    viewModel.removeBookReturn(book.code)
                                }
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Column {

                Button(
                    onClick = {
                        navController.navigate("scan_return_code") {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(start = 20.dp, end = 20.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2846CF))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.lanjut),
                            fontSize = 12.sp,
                            color = Color(0xffFFFFFF),
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(
                            imageVector = Icons.Default.Add ,
                            contentDescription = null,
                            tint = Color(0xffFFFFFF),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Button(
                    onClick = {
                        viewModel.submitReturnedBooks()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2846CF).copy(0.1f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.submit_buku),
                            fontSize = 12.sp,
                            color = Color(0xff111111).copy(0.6f),
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos ,
                            contentDescription = null,
                            tint = Color(0xff2846CF),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    )

    returnUiState.submitMessage?.let { message ->
        AlertDialog(
            onDismissRequest = {  },
            title = { Text("Pengembalian Buku Berhasil") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearSuccessFlag()
                    navController.navigate("main") {
                        popUpTo("return_list_book") { inclusive = true }
                    }
                }) {
                    Text("OK")
                }
            }
        )
    }

    returnUiState.errorMessage?.let { error ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Pengembalian Buku Gagal") },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearError()
                }) {
                    Text("OK")
                }
            }
        )
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
                color = Color.Black,
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