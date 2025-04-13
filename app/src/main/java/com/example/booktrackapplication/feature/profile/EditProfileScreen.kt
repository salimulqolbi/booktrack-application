package com.example.booktrack.feature.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booktrack.utils.NumberTextField
import com.example.booktrack.utils.PhoneNumberTextField
import com.example.booktrack.utils.ProfileNumberField
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily

@Composable
fun EditProfileScreen(imageRes: Int) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopProfileEditBar()
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(108.dp)
                                .clip(CircleShape)
                                .fillMaxSize()
                                .border(1.dp, Color(0xffEBEBEB), shape = CircleShape),
//                            contentScale = ContentScale.Crop
                        )

                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {

                                },
                            colors = CardDefaults.cardColors(Color.White),
                            border = BorderStroke(1.dp, Color(0xffEBEBEB))
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.delete_ic),
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.nama),
                        fontSize = 12.sp,
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProfileNumberField(
                        placeholder = stringResource(id = R.string.masukan_nama),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        onValueChange = {},
                        value = ""
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(id = R.string.nomor),
                        fontSize = 12.sp,
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PhoneNumberTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(39.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        onValueChange = {}
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(id = R.string.nis),
                        fontSize = 12.sp,
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    NumberTextField(
                        placeholder = stringResource(id = R.string.masukan_nis),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        onValueChange = {}
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(id = R.string.nisn),
                        fontSize = 12.sp,
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    NumberTextField(
                        placeholder = stringResource(id = R.string.masukan_nisn),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        onValueChange = {}
                    )
                }

                Spacer(modifier = Modifier.height(102.dp))

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2846CF))
                ) {
                    Text(
                        text = stringResource(R.string.simpan),
                        fontSize = 12.sp,
                        color = Color(0xffFFFFFF),
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    )
}

@Composable
fun TopProfileEditBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 40.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(Color(0XFFF7F8FC)),
            border = BorderStroke(1.dp, Color(0xffEBEBEB)),
            modifier = Modifier
                .size(40.dp)
                .clickable {}
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color(0xff2846CF),
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Text(
            text = "Edit Profile",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Card(
            shape = CircleShape,
            modifier = Modifier.size(36.dp),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color.Transparent,
                    modifier = Modifier.size(24.dp),

                    )
            }
        }
    }
}