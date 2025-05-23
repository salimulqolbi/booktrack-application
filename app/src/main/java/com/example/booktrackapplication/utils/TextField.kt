package com.example.booktrack.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booktrackapplication.R
import com.example.booktrackapplication.ui.theme.ManropeFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileNumberField(
    placeholder: String,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
//    val textValue = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                color = if (isError) Color(0x0DCC5252) else Color(0xffF7F8FC),
                shape = RoundedCornerShape(8.dp)
            )
            .then(
                if(isError) Modifier.border(1.dp, Color(0xffCC5252), shape = RoundedCornerShape(8.dp))
                else Modifier
            )
            .padding(start = 12.dp)
        // Padding agar tampilan lebih rapi
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // BasicTextField sebagai input nomor
            BasicTextField(
                value = value,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) { // Hanya menerima angka
//                        textValue.value = it
                        onValueChange(it)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = ManropeFamily,
                    color = Color(0xff888997)
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = ManropeFamily,
                                color = Color(0xff888997)
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.weight(1f) // Agar input bisa melebar
            )
        }
    }

    if (isError && !errorMessage.isNullOrEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            fontSize = 10.sp,
            fontFamily = ManropeFamily,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFCC5252)
        )
    }
}

@Composable
fun NumberTextField(
    placeholder: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val textValue = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                color = if (isError) Color(0x0DCC5252) else Color(0xffF7F8FC),
                shape = RoundedCornerShape(8.dp)
            )
            .then(
                if(isError) Modifier.border(1.dp, Color(0xffCC5252), shape = RoundedCornerShape(8.dp))
                else Modifier
            )
            .padding(start = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            // BasicTextField sebagai input nomor
            BasicTextField(
                value = textValue.value,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) { // Hanya menerima angka
                        textValue.value = it
                        onValueChange(it)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = ManropeFamily,
                    color = Color(0xff888997)
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (textValue.value.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = ManropeFamily,
                                color = Color(0xff888997)
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.weight(1f) // Agar input bisa melebar
            )
        }
    }

    if (isError && !errorMessage.isNullOrEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            fontSize = 10.sp,
            fontFamily = ManropeFamily,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFCC5252)
        )
    }
}

@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    errorText: String? = null,
    onValueChange: (String) -> Unit
) {
    val textValue = rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
            .height(40.dp)
            .background(Color(0xffF7F8FC), shape = RoundedCornerShape(8.dp))
            .padding(start = 12.dp)
        // Padding agar tampilan lebih rapi
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // BasicTextField sebagai input nomor
            BasicTextField(
                value = textValue.value,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) { // Hanya menerima angka
                        textValue.value = it
                        onValueChange(it)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = ManropeFamily,
                    color = Color(0xff888997)
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (textValue.value.isEmpty()) {
                            Text(
                                text = "08xxxxxxxxxx",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = ManropeFamily,
                                color = Color(0xff888997)
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.weight(1f) // Agar input bisa melebar
            )
        }
    }

    if (!errorText.isNullOrEmpty()) {
        Text(
            text = errorText,
            color = Color.Red,
            fontSize = 10.sp,
            fontFamily = ManropeFamily,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )
    }
}

    @Composable
    fun PasswordTextField(
        value: String,
        labelValue: String,
        modifier: Modifier = Modifier,
        leadingIcon: @Composable (() -> Unit)? = null,
        onValueChange: (String) -> Unit,
        isError: Boolean = false,
        errorMessage: String? = null
    ) {
        val localFocusManager = LocalFocusManager.current
        val password = rememberSaveable { mutableStateOf("") }
        val passwordVisible = rememberSaveable { mutableStateOf(false) }

        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            singleLine = true,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                localFocusManager.clearFocus()
            },
            modifier = modifier
                .background(
                    if(isError) Color(0x0DCC5252) else Color(0xffF7F8FC),
                    RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = if (isError) Color(0xFFCC5252) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Leading Icon jika ada
                    if (leadingIcon != null) {
                        leadingIcon()
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = labelValue,
                                fontSize = 10.sp,
                                color = Color(0xff959595),
                                fontFamily = ManropeFamily,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        innerTextField()
                    }

                    // Trailing Icon untuk toggle password visibility
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            painter = if (passwordVisible.value) painterResource(id = R.drawable.eye) else painterResource(id = R.drawable.eye_slash),
                            contentDescription = if (passwordVisible.value) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                        )
                    }
                }
            }
        )

        if (isError && !errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                fontSize = 10.sp,
                fontFamily = ManropeFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFCC5252)
            )
        }
    }