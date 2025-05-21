package com.example.booktrackapplication.utils

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily

@Composable
fun ScanBookCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    val gradientColors = listOf(
        Color(0xFF252F4A), // gelap (pojok kanan atas)
        Color(0xFF2846CF), // biru
        Color(0xFFE3E7FB), // biru muda terang
        Color(0xFFF2F4FC)  // putih terang
    )

    val gradientStops = listOf(
        0.0f to Color(0xFF252F4A),
        0.15f to Color(0xFF2846CF),
        0.5f to Color(0xFFE3E7FB),
        1.0f to Color(0xFFF2F4FC)
    )

    val gradientBrush = Brush.linearGradient(
        colorStops = gradientStops.toTypedArray(),
        start = Offset(850f, 0f), // kanan atas
        end = Offset(500f, 500f)    // kiri bawah
    )

    Card(
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 20.dp,
                end = 20.dp
            )
            .height(63.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = gradientBrush
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Ketahui Pemilik Buku Mapel",
                        style = MaterialTheme.typography.caption.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = ManropeFamily,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Mulai Scan untuk menemukan pemilik buku mapel.",
                        fontFamily = ManropeFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff111111).copy(0.7f)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .shadow(
                            elevation = 8.dp,
                            ambientColor = Color(0x402846CF),
                            spotColor = Color(0x402846CF)
                        )
                        .clickable(onClick = onClick)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffFFFFFF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.tabler_icon_line_scan),
                        contentDescription = "Scan",
                        tint = Color(0xff2846CF),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


