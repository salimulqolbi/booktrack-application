package com.example.booktrack.feature.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booktrack.utils.PasswordTextField
import com.example.booktrack.utils.ProfileNumberField
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = koinViewModel()
) {

    val state = viewModel.loginstrationUiState
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .height(104.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.biblioo_logo),
                        contentDescription = "Biblioo",
                        tint = Color(0xFF2846CF)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "Book Track",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E1E1E)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(top = 60.dp, start = 24.dp, end = 24.dp, bottom = 44.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.selamat_datang),
                        color = Color(0xFF1E1E1E),
                        fontSize = 32.sp,
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(id = R.string.masukan_identitas),
                        color = Color(0xFF959595),
                        fontSize = 12.sp,
                        fontFamily = ManropeFamily,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.nomor_identitas),
                            color = Color(0xff1E1E1E),
                            fontSize = 12.sp,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Medium,
                        )

                        ProfileNumberField(
                            value = state.nis,
                            placeholder = stringResource(R.string.masukan_nomor_identitas),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            onValueChange = {
                                viewModel.onNisLoginChange(it)
                            }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.kata_sandi),
                            color = Color(0xff1E1E1E),
                            fontSize = 12.sp,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Medium,
                        )

                        PasswordTextField(
                            labelValue = stringResource(R.string.masukan_kata_sandi),
                            value = state.password,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(39.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            onValueChange = {
                                viewModel.onPasswordChange(it)
                            }
                        )

                        Row(
                            modifier = Modifier
//                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Checkbox(
                                    onCheckedChange = { },
                                    modifier = Modifier
                                        .scale(0.8f)
                                        .size(20.dp),
                                    checked = false,
                                )

                                Text(
                                    text = stringResource(R.string.ingat_saya),
                                    fontSize = 10.sp,
                                    color = Color(0xff1E1E1E),
                                    fontFamily = ManropeFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Text(
                                text = stringResource(R.string.lupa),
                                fontSize = 10.sp,
                                color = Color(0xff1E1E1E),
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        viewModel.login { success ->
                            if (success) {

                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(200)
                                    navController.navigate("main") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    viewModel.loginstrationUiState.errorMessage ?: "Login gagal",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 44.dp, start = 24.dp, end = 24.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2846CF))
                ) {
                    Text(
                        text = stringResource(R.string.aktivasi_akun),
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