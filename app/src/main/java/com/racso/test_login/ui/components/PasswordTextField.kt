package com.racso.test_login.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.racso.test_login.R


@Composable
fun PasswordTextField(
    value: String,
    onShowPassword: () -> Unit,
    onValueChange: (String) -> Unit,
    showPassword: Boolean,
    placeholder: String,
    isError: Boolean,
    errorMessage: String?
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        placeholder = {
            Text(
                text = placeholder
            )
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Password,
                contentDescription = "Password Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = { onShowPassword() }) {
                Icon(
                    imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle password visibility"
                )
            }
        },
        onValueChange = { onValueChange(it) },
        isError = isError,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password
        ),
    )
    AnimatedVisibility (isError) {
        Text(
            text = errorMessage ?: "",
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Preview
@Composable
fun PasswordTextFieldPreview(){
    PasswordTextField(
        value = "",
        onShowPassword = { /*TODO*/ },
        onValueChange = { /*TODO*/ },
        showPassword = false,
        placeholder = "Enter password",
        isError = false,
        errorMessage = "Wrong password"
    )
}