package com.example.booktrack.feature.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booktrack.utils.PasswordTextField
import com.example.booktrack.utils.ProfileNumberField
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily
import com.example.booktrackapplication.utils.LoginWarningDialog
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

    val showDialog = state.errorMessage?.contains("Akun belum diaktivasi", ignoreCase = true) == true

    if (showDialog) {
        LoginWarningDialog(
            message = state.errorMessage!!,
            onDismissRequest = {
                viewModel.clearLoginError()
            },
            navController = navController

        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .background(Color.White)
                    .padding(top = 22.dp)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .height(112.dp)
                        .fillMaxWidth(),
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

                    //NIS
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(R.string.nomor_identitas),
                            color = Color(0xff1E1E1E),
                            fontSize = 12.sp,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Medium,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ProfileNumberField(
                            value = state.nis,
                            placeholder = stringResource(R.string.masukan_nomor_identitas),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            onValueChange = {
                                viewModel.onNisLoginChange(it)
                            },
                            isError = state.nisError != null,
                            errorMessage = state.nisError,
                        )
                    }

                    //PASSWORD
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = stringResource(R.string.kata_sandi),
                            color = Color(0xff1E1E1E),
                            fontSize = 12.sp,
                            fontFamily = ManropeFamily,
                            fontWeight = FontWeight.Medium,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        PasswordTextField(
                            labelValue = stringResource(R.string.masukan_kata_sandi),
                            value = state.password,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(39.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            onValueChange = {
                                viewModel.onPasswordChange(it)
                            },
                            isError = state.passError != null,
                            errorMessage = state.passError,
                        )
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
                        text = stringResource(R.string.masuk),
                        fontSize = 12.sp,
                        color = Color(0xffFFFFFF),
                        fontFamily = ManropeFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp, end = 60.dp, bottom = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                ActivationText {
                    navController.navigate("register") {
                        launchSingleTop = true
                    }
                }
            }
        }
    )
}

@Composable
fun ActivationText(onActivateClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xff111111).copy(alpha = 0.4f), fontSize = 14.sp)) {
            append("Belum aktivasi akun? ")
        }

        pushStringAnnotation(tag = "ACTIVATE", annotation = "activate")
        withStyle(style = SpanStyle(color = Color(0xff111111), fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
            append("Aktivasi")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "ACTIVATE", start = offset, end = offset)
                .firstOrNull()?.let {
                    onActivateClick()
                }
        }
    )
}
