package com.example.booktrackapplication.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

val warningList = listOf(
    "Kehilangan Buku pada saat di area sekolah maupun diluar sekolah.",
    "Terjadi pertukaran buku mata pelajaran secara tidak sengaja oleh beberapa pihak.",
    "Mendapatkan musibah pada saat memiliki buku mata pelajaran (cont. kebanjiran).",
    "Peminjaman Buku yang Tidak Dikembalikan Tepat Waktu.",
    "Buku Mata Pelajaran Tertinggal di Lokasi yang Sulit Dijangkau."
)


@Composable
fun LoanWarningDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = Color(0XFFF7F8FC),
                        border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "⚠\uFE0F"
                            )


                            Text(
                                "PENTING!",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    Card(
                        shape = CircleShape,
                        backgroundColor = Color(0XFFF7F8FC),
                        border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onDismissRequest() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xff2846CF),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }


                Text(
                    buildAnnotatedString {
                        append("Kepada seluruh siswa - siswi SMKN 7 Semarang yang bertanggung jawab dalam pemakaian buku mapel harap ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("melaporkan : ")
                        }
                    },
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 20.dp,
                        end = 34.dp
                    )


                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 20.dp, end = 20.dp)
                ) {
                    warningList.forEach { warning ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = null,
                                tint = Color(0xff2846CF),
                                modifier = Modifier
                                    .size(8.dp)
                                    .alignBy(FirstBaseline)
                                    .padding(top = 2.dp)
                            )


                            Text(
                                text = warning,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF111111).copy(0.6f),
                            )
                        }
                    }
                }


                Text(
                    buildAnnotatedString {
                        append("Sesuai dengan keadaan diatas, harap segera menuju ke pihak ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xff2846CF),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("admin di Perpustakaan ")
                        }
                        append("SMKN 7 Semarang pada saat jam kerja.")
                    },
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(
                            start = 34.dp,
                            top = 36.dp,
                            end = 14.dp,
                            bottom = 20.dp
                        )
                )
            }
        }
    }
}

val warningList2 = listOf(
    "Anda tidak dapat melakukan scan buku baru karena masih ada buku yang belum dikembalikan. Mohon" +
            "kembalikan buku sebelumnya terlebih dahulu.",
    "Periksa buku yang akan Anda scan untuk memastikan kondisinya",
    "Jika ada kendala, segera hubungi petugas perpustakaan."
)


@Composable
fun ScanWarningDialog(
    onDismissRequest: () -> Unit,
    navController: NavController,
) {

    val viewModel: MainViewmodel = koinViewModel()

    val uiState by viewModel.uiState.collectAsState()
    val returnUiState by viewModel.returnUiState.collectAsStateWithLifecycle()

    LaunchedEffect(returnUiState.isSuccess) {
        if(returnUiState.isSuccess) {
            navController.navigate("scan_return_code")
            viewModel.clearSuccessFlag()
        }
    }

    if (returnUiState.errorMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            },
            title = { Text("Gagal") },
            text = { Text(returnUiState.errorMessage ?: "") }
        )
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate("scan_code")
            viewModel.clearSuccessFlag()
        }
    }

    if (uiState.errorMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            },
            title = { Text("Gagal") },
            text = { Text(uiState.errorMessage ?: "") }
        )
    }

    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = Color(0XFFF7F8FC),
                        border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "⚠\uFE0F"
                            )

                            Text(
                                "PENTING!",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    Card(
                        shape = CircleShape,
                        backgroundColor = Color(0XFFF7F8FC),
                        border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onDismissRequest() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xff2846CF),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Text(
                    text = stringResource(id = R.string.kepada),
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 20.dp,
                        end = 34.dp
                    )

                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 20.dp, end = 20.dp)
                ) {
                    warningList2.forEach { warning ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = null,
                                tint = Color(0xff2846CF),
                                modifier = Modifier
                                    .size(8.dp)
                                    .alignBy(FirstBaseline)
                                    .padding(top = 2.dp)
                            )

                            Text(
                                text = warning,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF111111).copy(0.6f),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        viewModel.validateRetuningDate()
                    },
                    border = BorderStroke(1.dp, Color(0xffE1E1E1)),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff2846CF)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                ) {
                    if(returnUiState.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(
                            "Kembalikan Buku",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Button(
                    onClick = {
                        viewModel.checkBorrowStatusValidation()
                    },
                    border = BorderStroke(1.dp, Color(0xffE1E1E1)),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff2846CF)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                ) {
                    if(uiState.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(
                            "Pinjam Buku",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//fun LogoutWarning(
//    onDismissRequest: () -> Unit,
//    onConfirm: () -> Unit,
//) {
//    Dialog(
//        onDismissRequest = { onDismissRequest() }
//    ) {
//        Box(
//            modifier = Modifier
//                .clip(RoundedCornerShape(12.dp))
//                .background(Color.White)
//                .padding(16.dp)
//                .width(260.dp)
//        ) {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(12.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = "Konfirmasi Logout",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                    color = Color.Black,
//                    fontFamily = ManropeFamily
//                )
//                Text(
//                    text = "Apakah kamu yakin ingin Logout dari akun ini? ",
//                    fontSize = 14.sp,
//                    color = Color.Gray,
//                    textAlign = TextAlign.Center,
//                    fontFamily = ManropeFamily
//                )
//            }
//
//            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
////                    TextButton(onClick = onDismissRequest) {
////                        Text(
////                            "Batal",
////                            color = Color(0xFF2196F3),
////
////                        )
////                    }
////                    TextButton(onClick = onConfirm) {
////                        Text("Oke", color = Color(0xFF2196F3))
////                    }
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .clickable { onDismissRequest() }
//                        .fillMaxHeight(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        "Batal",
//                        color = Color(0xff2846CF),
//                        fontFamily = ManropeFamily
//                    )
//                }
//
//                // Garis vertikal pemisah
//                Box(
//                    modifier = Modifier
//                        .width(1.dp)
//                        .fillMaxHeight()
//                        .background(Color(0xFFE0E0E0))
//                )
//
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .clickable { onConfirm() }
//                        .fillMaxHeight(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        "Oke",
//                        color = Color(0xff2846CF),
//                        fontFamily = ManropeFamily
//                    )
//                }
//            }
//        }
//    }
//}

@Composable
fun LogoutWarning(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .width(260.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Konfirmasi Logout",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = ManropeFamily
                    )
                    Text(
                        text = "Apakah kamu yakin ingin Logout dari akun ini?",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontFamily = ManropeFamily
                    )
                }

                // Divider atas tombol (mentok kanan kiri)
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onDismissRequest() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Batal",
                            color = Color(0xFF2196F3),
                            fontFamily = ManropeFamily
                        )
                    }

                    // Divider tengah (mentok atas bawah)
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(Color(0xFFE0E0E0))
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onConfirm() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Oke",
                            color = Color(0xFF2196F3),
                            fontFamily = ManropeFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginWarning(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login Gagal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = ManropeFamily
                    )
                    Text(
                        text = "Password yang anda masukan salah, silahkan coba lagi",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontFamily = ManropeFamily
                    )
                }

                // Divider atas tombol (mentok kanan kiri)
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onDismissRequest() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Coba Lagi",
                        color = Color(0xFF2196F3),
                        fontFamily = ManropeFamily
                    )
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DialogPrev() {
//    LogoutWarning {  }
//}