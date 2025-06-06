package com.example.booktrack.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily

@Composable
fun HomeCard(
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(start = 20.dp, end = 20.dp, top = 16.dp)
    ){
       Box(
           modifier = Modifier.fillMaxWidth()
               .paint(
                   painter = painterResource(id = R.drawable.bg_home_card),
               )
       ){

           Column {
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(start = 16.dp, top = 12.dp, end = 16.dp),
                   horizontalArrangement = Arrangement.SpaceBetween,
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Card(
                       shape = RoundedCornerShape(32),
                       border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                       colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.2f)),
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
           }
       }
    }
}

@Preview(showBackground = true)
@Composable
fun HCPreview() {
    HomeCard()
}