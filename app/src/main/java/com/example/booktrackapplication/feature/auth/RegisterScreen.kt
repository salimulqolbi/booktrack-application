package com.example.booktrack.feature.login

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booktrack.utils.NumberTextField
import com.example.booktrack.utils.PasswordTextField
import com.example.booktrack.utils.PhoneNumberTextField
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.viewmodel.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val state = viewModel.registrationUiState

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .background(Color.White)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .height(112.dp)
                        .fillMaxWidth()
                        .padding(top = 44.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.biblioo_logo),
                        contentDescription = "Book Track",
                        tint = Color(0xFF2846CF)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Book Track",
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
                        text = stringResource(id = R.string.aktivasi),
                        color = Color(0xFF1E1E1E),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = ManropeFamily
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(id = R.string.akun_anda),
                        fontFamily = ManropeFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF959595)
                    )
                }

                //TEXT FIELD
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                                .height(39.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            onValueChange = {
                                viewModel.onNisChange(it)
                            },
//                            isError = state.isError,
//                            errorMessage = state.errorMessage
                            isError = state.nisError != null,
                            errorMessage = state.nisError
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

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
                            onValueChange = {
                                viewModel.onPhoneNumberChange(it)
                            },
                            errorText = state.generalError
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    //PASSWORD AKTIVASI
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.kata_sandi_lama),
                            fontSize = 12.sp,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        PasswordTextField(
                            labelValue = stringResource(id = R.string.kata_sandi_aktivasi),
                            value = state.oldPassword,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(39.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            onValueChange = {
                                viewModel.onOldPasswordChange(it)
                            },
                            isError = state.passwordError != null,
                            errorMessage = state.passwordError
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    //NEW PASSWORD
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.kata_sandi_baru),
                            fontSize = 12.sp,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        PasswordTextField(
                            labelValue = stringResource(id = R.string.kata_sandi_aktivasi),
                            value = state.newPassword,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(39.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            onValueChange = {
                                viewModel.onNewPasswordChange(it)
                            },
                            isError = state.newPasswordError != null,
                            errorMessage = state.newPasswordError
                        )
                    }
                }

                Spacer(modifier = Modifier.height(44.dp))

                Button(
                    onClick = {
                        viewModel.activeAccount { success ->
                            if (success) {
                                navController.navigate("login") {
                                    popUpTo("register") {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 44.dp, start = 24.dp, end = 24.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2846CF)),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(
                            text = stringResource(R.string.aktivasi),
                            fontSize = 12.sp,
                            color = Color(0xffFFFFFF),
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    )
}