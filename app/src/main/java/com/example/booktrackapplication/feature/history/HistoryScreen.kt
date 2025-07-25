package com.example.booktrack.feature.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.booktrack.utils.noRippleClickable
import com.example.booktrackapplication.data.response.HistoryItem
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    viewmodel: MainViewmodel = koinViewModel()
) {

    val state by viewmodel.historyUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.getHistory()
    }

    Column(
        modifier = Modifier
            .padding(top = 36.dp, start = 20.dp, end = 20.dp)
            .verticalScroll(rememberScrollState()),
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

        if(state.history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Tidak ada buku yang dibawa pada 1 semester lalu",
                    fontFamily = ManropeFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        } else if (state.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            state.history.forEach { (semester, items) ->

                var expanded by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable { expanded = !expanded },
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
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Semester $semester",
                                            fontFamily = ManropeFamily,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(
                                                start = 12.dp,
                                                top = 7.dp,
                                                bottom = 7.dp
                                            )
                                        )

                                        Icon(
                                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(
                                                    end = 12.dp,
                                                    top = 7.dp,
                                                    bottom = 7.dp
                                                )
                                        )
                                    }

                                    AnimatedVisibility(visible = expanded) {
                                        Column {
                                            Divider()

                                            items.forEachIndexed { index, book ->
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
                                                        book.title,
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
                                                    ) {
                                                        Text(
                                                            book.code,
                                                            modifier = Modifier
                                                                .padding(horizontal = 16.dp, vertical = 4.dp),
                                                            fontSize = 10.sp,
                                                            color = Color.Black,
                                                            fontFamily = ManropeFamily,
                                                            fontWeight = FontWeight.SemiBold
                                                        )
                                                    }

                                                }

                                                if (index != items.lastIndex) {
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
            }
        }
    }
}

@Composable
fun SemesterCard(
    semester: String,
    items: List<HistoryItem>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .background(Color(0xffF7F8FC))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = semester,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ManropeFamily,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null
            )
        }

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                items.forEachIndexed { index, book ->
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
                            book.title,
                            fontFamily = ManropeFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )

                        Card(
                            shape = RoundedCornerShape(80),
                            border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                            backgroundColor = Color(0xffF7F8FC)
                        ) {
                            Text(
                                book.code,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                                fontSize = 10.sp,
                                color = Color.Black,
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    if (index != items.lastIndex) {
                        Divider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
