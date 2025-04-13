package com.example.booktrack.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
            .height(202.dp)
            .padding(start = 20.dp, end = 20.dp, top = 16.dp)
    ){
       Box(
           modifier = Modifier.fillMaxWidth()
       ){
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Card Event",
                modifier = Modifier.fillMaxSize()
            )

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
                       border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                       colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.1f)),
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

               Button(
                   onClick = {
//                       navController.navigate("schedule_list")
                   },
                   colors = ButtonDefaults.buttonColors(
                       containerColor = Color(0xff2846CF)
                   ),
                   modifier = Modifier
//                                .fillMaxWidth()
                       .padding(start = 16.dp, top = 20.dp)
                       .height(36.dp)
                       .width(120.dp),
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
                           fontWeight = FontWeight.Bold,
                           fontSize = 12.sp,
                           color = Color.White
                       )

//                       Spacer(modifier = Modifier.width(4.dp))

                       Icon(
                           painter = painterResource(id = R.drawable.tabler_icon_arrow_narrow_right),
                           contentDescription = "See",
                           modifier = Modifier.size(14.dp)
                       )
                   }
               }
           }
       }
    }
}

@Preview(showBackground = true)
@Composable
fun HCPreview() {
    HomeCard()
}