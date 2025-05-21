package com.example.booktrack.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booktrackapplication.model.NewsItems
import com.example.booktrackapplication.ui.theme.ManropeFamily

@Composable
fun NewsList(newsList: List<NewsItems>,onNewsClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 57.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        newsList.forEach { news ->
            NewsCard(
                newsItems = news,
                onNewsClick = {onNewsClick()}
            )
//            Spacer(modifier = Modifier.height(12.dp))
            Divider()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewsCard(newsItems: NewsItems, modifier: Modifier = Modifier, onNewsClick: () -> Unit = {}) {
    Row(
        modifier.fillMaxWidth().clickable { onNewsClick() }
    ) {
        Image(
            painter = painterResource(newsItems.image),
            contentDescription = newsItems.title,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(76.dp)
        )

        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text(
                text = newsItems.title,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                fontFamily = ManropeFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = newsItems.date,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF777777),
                    fontFamily = ManropeFamily
                )

                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = " ",
                    modifier = Modifier.size(4.dp),
                    tint = Color(0xFFD9D9D9)
                )

                Text(
                    text = newsItems.time,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF777777),
                    fontFamily = ManropeFamily
                )
            }

            FlowRow(
//                modifier = Modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                newsItems.tags.forEach{ tag ->
                    Card(
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, Color.LightGray),
                        backgroundColor = Color(0xFFF8F8F8),
                        modifier = Modifier.clickable {  }
                    ) {
                        Text(
                            text = tag,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            color = Color.Gray,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}