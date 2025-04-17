package com.example.booktrack.feature.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.booktrack.utils.jurusanAcronym
import com.example.booktrack.utils.noRippleClickable
import com.example.booktrack.utils.toFormattedDate
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScheduleList(
    navController: NavController,
    viewModel: MainViewmodel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getSchedule(
            onSuccess = {
                Log.d("Schedule", "Data berhasil diambil")
            },
            onError = {
                Log.e("Schedule", "Gagal ambil data: $it")
            }
        )
    }

    val schedules = state.schedules
    val jurusanList = schedules.map { it.department }.distinct().sorted()

    val jurusanExpanded = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        backgroundColor = Color.White,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
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
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = "Back",
                        tint = Color(0xff2846CF),
                    )
                }

                Text(
                    "Detail Jadwal",
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
                    .padding(paddingValues)
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (state.errorMessage != null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Gagal memuat jadwal: ${state.errorMessage}")
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            jurusanList.forEachIndexed { index, jurusan ->

                                val isFirst = index == 0
                                val isLast = index == jurusanList.lastIndex

                                val cardShape = when {
                                    isFirst && isLast -> RoundedCornerShape(8.dp) // Cuma satu item, full rounded
                                    isFirst -> RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                                    isLast -> RoundedCornerShape(
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )

                                    else -> RoundedCornerShape(0.dp) // Tengah-tengah, gak perlu rounded
                                }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = cardShape
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        // Header - Nama Jurusan
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Color(0xFFEFF3FA))
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = jurusanAcronym(jurusan),
                                                fontWeight = FontWeight.Bold,
                                            )
                                            Log.d("DEBUG_JURUSAN", jurusan)
                                            Icon(
                                                imageVector = if (jurusanExpanded[jurusan] == true) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                                contentDescription = null,
                                                modifier = Modifier.noRippleClickable {
                                                    jurusanExpanded[jurusan] =
                                                        !(jurusanExpanded[jurusan] ?: false)
                                                },
                                                tint = Color.Blue
                                            )
                                        }

                                        Divider()

                                        if (jurusanExpanded[jurusan] == true) {
                                            schedules.filter { it.department == jurusan }
                                                .sortedBy { it.classIndex }
                                                .forEach { item ->
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .background(Color.White)
                                                    ) {
                                                        val kelas =
                                                            "X ${jurusanAcronym(item.department)} ${item.classIndex}"

                                                        Text(
                                                            text = kelas,
                                                            fontWeight = FontWeight.Bold,
                                                            modifier = Modifier.padding(
                                                                start = 12.dp,
                                                                top = 14.dp,
                                                                bottom = 14.dp
                                                            )
                                                        )
                                                        Divider(
                                                            color = Color(0xFFE0E0E0),
                                                            thickness = 1.dp
                                                        )

                                                        DetailRow(
                                                            label = "Peminjaman",
                                                            value = item.borrowDate.toFormattedDate()
                                                        )
                                                        Divider(
                                                            color = Color(0xFFE0E0E0),
                                                            thickness = 1.dp
                                                        )

                                                        DetailRow(
                                                            label = "Pengembalian",
                                                            value = item.returnDate.toFormattedDate()
                                                        )
                                                        Divider(
                                                            color = Color(0xFFE0E0E0),
                                                            thickness = 1.dp
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
            }

//            Column(
//                modifier = Modifier
//                    .padding(paddingValues)
//                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                if (schedules.isEmpty()) {
//                    Text("Tidak ada jadwal tersedia.")
//                    Log.d("ScheduleList", "Jadwal masuk: ${schedules.size}")
//                } else {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
//                            .clip(RoundedCornerShape(8.dp))
//                    ) {
//                        Column(
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            jurusanList.forEachIndexed { index, jurusan ->
//
//                                val isFirst = index == 0
//                                val isLast = index == jurusanList.lastIndex
//
//                                val cardShape = when {
//                                    isFirst && isLast -> RoundedCornerShape(8.dp) // Cuma satu item, full rounded
//                                    isFirst -> RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
//                                    isLast -> RoundedCornerShape(
//                                        bottomStart = 8.dp,
//                                        bottomEnd = 8.dp
//                                    )
//
//                                    else -> RoundedCornerShape(0.dp) // Tengah-tengah, gak perlu rounded
//                                }
//
//                                Card(
//                                    modifier = Modifier
//                                        .fillMaxWidth(),
//                                    shape = cardShape
//                                ) {
//                                    Column(
//                                        modifier = Modifier.fillMaxWidth()
//                                    ) {
//                                        // Header - Nama Jurusan
//                                        Row(
//                                            modifier = Modifier
//                                                .fillMaxWidth()
//                                                .background(Color(0xFFEFF3FA))
//                                                .padding(16.dp),
//                                            horizontalArrangement = Arrangement.SpaceBetween,
//                                            verticalAlignment = Alignment.CenterVertically
//                                        ) {
//                                            Text(
//                                                text = jurusanAcronym(jurusan),
//                                                fontWeight = FontWeight.Bold,
//                                            )
//                                            Log.d("DEBUG_JURUSAN", jurusan)
//                                            Icon(
//                                                imageVector = if (jurusanExpanded[jurusan] == true) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
//                                                contentDescription = null,
//                                                modifier = Modifier.noRippleClickable {
//                                                    jurusanExpanded[jurusan] =
//                                                        !(jurusanExpanded[jurusan] ?: false)
//                                                },
//                                                tint = Color.Blue
//                                            )
//                                        }
//
//                                        Divider()
//
//                                        if (jurusanExpanded[jurusan] == true) {
//                                            schedules.filter { it.department == jurusan }
//                                                .sortedBy { it.classIndex }
//                                                .forEach { item ->
//                                                    Column(
//                                                        modifier = Modifier
//                                                            .fillMaxWidth()
//                                                            .background(Color.White)
//                                                    ) {
//                                                        val kelas =
//                                                            "X ${jurusanAcronym(item.department)} ${item.classIndex}"
//
//                                                        Text(
//                                                            text = kelas,
//                                                            fontWeight = FontWeight.Bold,
//                                                            modifier = Modifier.padding(
//                                                                start = 12.dp,
//                                                                top = 14.dp,
//                                                                bottom = 14.dp
//                                                            )
//                                                        )
//                                                        Divider(
//                                                            color = Color(0xFFE0E0E0),
//                                                            thickness = 1.dp
//                                                        )
//
//                                                        DetailRow(
//                                                            label = "Peminjaman",
//                                                            value = item.borrowDate.toFormattedDate()
//                                                        )
//                                                        Divider(
//                                                            color = Color(0xFFE0E0E0),
//                                                            thickness = 1.dp
//                                                        )
//
//                                                        DetailRow(
//                                                            label = "Pengembalian",
//                                                            value = item.returnDate.toFormattedDate()
//                                                        )
//                                                        Divider(
//                                                            color = Color(0xFFE0E0E0),
//                                                            thickness = 1.dp
//                                                        )
//                                                    }
//                                                }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontFamily = ManropeFamily,
            fontWeight = FontWeight.Medium,
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier
                .padding(start = 20.dp, top = 8.dp, bottom = 8.dp)
                .weight(1f)
        )

        Divider(
            color = Color(0xFFE0E0E0),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Text(
            text = value,
            fontSize = 10.sp,
            fontFamily = ManropeFamily,
            fontWeight = FontWeight.Medium,
            color = Color.Black.copy(alpha = 0.8f),
            modifier = Modifier
                .padding(start = 20.dp, top = 8.dp, bottom = 8.dp)
                .weight(1f)
        )
    }
}