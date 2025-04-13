package com.example.booktrack.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booktrack.utils.noRippleClickable
import com.example.booktrackapplication.R
import com.example.booktrackapplication.data.datastore.questionCategories
import com.example.booktrackapplication.data.datastore.questionsByCategory
import com.example.booktrackapplication.ui.theme.ManropeFamily

@Composable
fun FaqScreen(navController: NavController) {

    var selectedCategory by rememberSaveable {
        mutableStateOf<String?>(
            questionCategories.firstOrNull()
        )
    }
    var expandedQuestion by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(selectedCategory) {
        expandedQuestion = null
    }

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
                    "FAQ",
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
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            "Hai sobat stemba\uD83D\uDCA5, Bagaimana kami membantumu?",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff111111).copy(0.7f),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            questionCategories.forEach { category ->
                                val isSelected = selectedCategory == category
                                val backgorungColor =
                                    if (isSelected) Color(0xff2846CF) else Color(0xffF7F8FC)

                                Card(
                                    shape = RoundedCornerShape(50.dp),
                                    colors = CardDefaults.cardColors(backgorungColor),
                                    border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(107.dp)
                                        .then(
                                            if (!isSelected) Modifier.noRippleClickable {
                                                selectedCategory = category
                                            } else Modifier
                                        )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = category,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color(0xffffffff) else Color(
                                                0xff9A9A9B
                                            ),
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(
                                                vertical = 6.dp,
                                                horizontal = 14.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        val filteredQuestions =
                            selectedCategory?.let { questionsByCategory[it] } ?: emptyList()

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            filteredQuestions.forEach { faqItem ->
                                val isExpanded = expandedQuestion == faqItem.question

                                QuestionList(
                                    question = faqItem.question,
                                    answer = faqItem.answer,
                                    isExpanded = isExpanded,
                                    onExpandChange = { expanded ->
                                        expandedQuestion = if (expanded) faqItem.question else null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp, end = 60.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_6),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    buildAnnotatedString {
                        append("Butuh informasi lebih lanjut? Hubungi kami di ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xff2846CF),
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append("admin@smkn7semarang.sch.id")
                        }
                    },
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    color = Color(0xff111111).copy(alpha = 0.7f)
                )
            }
        }
    )
}

@Composable
fun QuestionList(
    question: String,
    answer: String,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(
                text = question,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier.noRippleClickable {
                    onExpandChange(!isExpanded)
                }
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = answer,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xff777777)
                )
                Divider(
                    color = Color(0xFFE0E0E0),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        if (!isExpanded) {
            Divider(
                color = Color(0xFFE0E0E0),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}