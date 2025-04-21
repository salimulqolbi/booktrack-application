package com.example.booktrack.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily

@Composable
fun AboutScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        backgroundColor = Color.White,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
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
                    "Tentang Kami",
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
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ){
                Text(
                    stringResource(id = R.string.paragraf_1),
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xff031103),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
                Text(
                    stringResource(id = R.string.paragraf_2),
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xff031103),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
                Text(
                    stringResource(id = R.string.paragraf_3),
                    fontFamily = ManropeFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xff031103),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            }
        }
    )
}