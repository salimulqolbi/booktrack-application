package com.example.booktrack.feature.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booktrack.utils.noRippleClickable
import com.example.booktrackapplication.ui.theme.ManropeFamily

val mapel = listOf(
    "Bahasa Indonesia",
    "Matematika",
    "Bahasa Kawa",
    "Sejarah",
    "Bahasa Inggris",
    "PKN",
    "Pendidikan Agama Islam",
    "Produk Kreatif & Kewirausahaan",
    "IPAS",
    "Cyber Security",
    "Pemrograman Dasar",
    "Peembuatan Kerangka Website",
    "Dessign Thinking"
)

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier
            .padding(top = 36.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Riwayat",
            fontFamily = ManropeFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 30.dp),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xffE0E0E0))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color(0xffF7F8FC),
                                    )
                            ) {
                                Text(
                                    "Buku Mata Pelajaran",
                                    fontFamily = ManropeFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(
                                        start = 12.dp,
                                        top = 7.dp,
                                        bottom = 7.dp
                                    )
                                )
                            }

                            Divider()

                            mapel.forEachIndexed { index, book ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 16.dp,
                                            bottom = 12.dp
                                        )
                                        .noRippleClickable { },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        book,
                                        fontFamily = ManropeFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.weight(1f)
                                    )

                                    Card(
                                        shape = RoundedCornerShape(80),
                                        border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                                        backgroundColor = Color(0xffF7F8FC),
                                        modifier = Modifier
//                                                .height(23.dp)
                                    ) {
                                        Text(
                                            "BA - 7862 - SD76",
                                            modifier = Modifier
//                                                    .weight(1f)
                                                .padding(horizontal = 16.dp, vertical = 4.dp),
                                            fontSize = 10.sp,
                                            color = Color.Black,
                                            fontFamily = ManropeFamily,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                }

                                if (index != mapel.lastIndex) {
                                    Divider(
                                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
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