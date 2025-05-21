package com.example.booktrack.feature.profile

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.LocalActivity
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.booktrackapplication.utils.LogoutWarning
import com.example.booktrackapplication.utils.convertToRoman
import com.example.booktrackapplication.utils.extractAbbreviation
import com.example.booktrackapplication.viewmodel.MainViewmodel
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = koinViewModel(),
    mainModel: MainViewmodel = koinViewModel()
) {

    val user by mainModel.userUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        mainModel.getUser()
    }

    val nama = user.user?.name ?: " Anonim"
    val nis = user.user?.nis ?: "NIS tidak tersedia"
    val kelas = user.user?.grade ?: "Kelas tidak diketahui"
    val index = user.user?.classIndex ?: " "
    val jurusan = user.user?.departmentId ?: " "

    val image = R.drawable.anonimus

    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (user.user == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val menuItems = listOf(
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
                        text = "$nama",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$nis - ${convertToRoman(kelas.toString())} ${extractAbbreviation(jurusan)} $index",
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
                    CardProfileMenuList(menuItems, navController = navController)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0XFFF7F8FC)
                    ),
                    border = BorderStroke(1.dp, Color(0xffEBEBEB)),
                    onClick = {
                        showDialog = true
//                        viewModel.logout { success ->
//                            if (success) {
//                                navController.navigate("login") {
//                                    popUpTo("main") { inclusive = true }
//                                }
//                            } else {
//                                Toast.makeText(context, "Logout gagal", Toast.LENGTH_SHORT).show()
//                            }
//                        }
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

                if(showDialog) {
                    LogoutWarning(
                        onDismissRequest = { showDialog = false },
                        onConfirm = {
                            viewModel.logout { success ->
                                if (success) {
                                    navController.navigate("login") {
                                        popUpTo("main") { inclusive = true }
                                    }
                                } else {
                                    Toast.makeText(context, "Logout gagal", Toast.LENGTH_SHORT).show()
                                }
                            }
                            showDialog = false
                        }
                    )
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
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Profile",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CardProfileMenu(
    icon: ImageVector,
    title: String,
    onClick: (() -> Unit)? = null
) {

    val modifier = if (onClick != null) {
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(bottom = 12.dp)
    } else {
        Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    }

    Column {
        Row(
            modifier = modifier,
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
fun CardProfileMenuList(
    menuItems: List<Pair<ImageVector, String>>,
    navController: NavController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 19.dp, end = 19.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        menuItems.forEach { (icon, title) ->
            val onClick: (() -> Unit)? = when (title) {

                "FAQ" -> {
                    {
                        navController.navigate("faq") {
                            popUpTo("profile") {
                                inclusive = true
                            }
                        }
                    }
                }

                "Hubungi Kami" -> {
                    {
                        val url = "https://wa.me/628813718712"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                }

                "Tentang Stemba Book Track" -> {
                    {
                        navController.navigate("about_us") {
                            popUpTo("profile") {
                                inclusive = true
                            }
                        }
                    }
                }

                else -> null
            }
            CardProfileMenu(icon = icon, title = title, onClick = onClick)
        }
    }
}