package com.example.booktrack.feature.profile

import android.widget.Toast
import androidx.activity.compose.LocalActivity
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.booktrackapplication.R
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = koinViewModel()
) {

    val user by viewModel.user.collectAsStateWithLifecycle()

    val name = user?.name ?: "Anonim"
    val nis = user?.nis ?: "NIS tidak tersedia"
    val kelas = user?.grade ?: "Kelas tidak diketahui"
    val index = user?.classIndex ?: " "
    val jurusan = user?.departmentId ?: " "
    val image = R.drawable.anonimus

    val context = LocalContext.current


    val activity = LocalActivity.current

    val menuItems = listOf(
        Icons.Outlined.CheckCircle to "Aktivasi Akun",
        Icons.Outlined.Language to "Bahasa",
        Icons.Outlined.QuestionAnswer to "FAQ",
        Icons.Outlined.HeadsetMic to "Hubungi Kami",
        Icons.Outlined.Info to "Tentang Stemba Book Track"
    )
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        topBar = {
            TopAppBar()
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
                            painter = painterResource(id = image),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color(0xffEBEBEB), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            onClick = {

                            },
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color(0xffEBEBEB), CircleShape)
                                .padding(0.5.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "Edit Profile",
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "$name - $nis",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$kelas - $jurusan $index",
                        fontSize = 12.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(Color(0XFFF7F8FC)),
                    border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                ) {
                    CardProfileMenuList(menuItems)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0XFFF7F8FC)
                    ),
                    border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                    onClick = {
                        viewModel.logout { success ->
                            if (success) {
                                navController.navigate("login") {
                                    popUpTo("main") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Logout gagal", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = null,
                            tint = Color(0xffCC5252)
                        )
                        Text(
                            "Log Out",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 40.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(Color.Transparent),
//            border = BorderStroke(1.dp, Color(0xffEBEBEB)),
            modifier = Modifier.size(44.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Transparent,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            text = "Profile",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(Color(0XFFF7F8FC)),
            border = BorderStroke(1.dp, Color(0xffEBEBEB)),
            modifier = Modifier.size(44.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun CardProfileMenu(icon: ImageVector, title: String) {
    Column {
        Row(
            modifier = Modifier.padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Divider()
    }
}

@Composable
fun CardProfileMenuList(menuItems: List<Pair<ImageVector, String>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 19.dp, end = 19.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        menuItems.forEach { (icon, title) ->
            CardProfileMenu(icon = icon, title = title)
        }
    }
}