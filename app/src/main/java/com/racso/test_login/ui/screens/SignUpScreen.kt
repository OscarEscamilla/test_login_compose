package com.racso.test_login.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.racso.test_login.R
import com.racso.test_login.ui.theme.TestloginTheme
import com.racso.test_login.ui.components.EmailTextField
import com.racso.test_login.ui.components.PasswordTextField
import com.racso.test_login.ui.components.PhoneTextField
import com.racso.test_login.ui.components.ScreenTitleText
import com.racso.test_login.utils.SIZE_EXTRA_LARGE
import com.racso.test_login.utils.SIZE_EXTRA_SMALL
import com.racso.test_login.utils.SIZE_LARGE
import com.racso.test_login.utils.SIZE_MEDIUM
import com.racso.test_login.utils.isValidEmail
import com.racso.test_login.utils.isValidPassword
import com.racso.test_login.utils.isValidPhoneNumber
import com.racso.test_login.viewmodels.AuthViewModel

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    val signupResult by authViewModel.signupResult.collectAsState()
    signupResult.onSuccess { user ->
        if (user != null) {
            onSignUpSuccess()
            Toast.makeText(LocalContext.current, "Cuenta creada exitosamente.", Toast.LENGTH_SHORT)
                .show()
        }
    }.onFailure { exception ->
        // Show error message
        Toast.makeText(
            LocalContext.current,
            exception.message ?: "Sign up failed",
            Toast.LENGTH_SHORT
        ).show()
    }

    SignUp(
        btnSignUpOnClick = { email, password, phone ->
            authViewModel.signUp(email, password, phone)
        },
        onNavigateToLogin = onNavigateToLogin
    )
}


@Composable
fun SignUp(
    btnSignUpOnClick: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit
) {

    var emailValue by rememberSaveable { mutableStateOf("") }
    var phoneValue by rememberSaveable { mutableStateOf("") }
    var passwordValue by rememberSaveable { mutableStateOf("") }
    var passwordConfirmValue by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var showConfirmPassword by rememberSaveable { mutableStateOf(false) }


    // State for validation errors
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var phoneError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordConfirmError by rememberSaveable { mutableStateOf<String?>(null) }

    val invalidEmailMessage = stringResource(id = R.string.invalid_email)
    val invalidPasswordMessage = stringResource(id = R.string.invalid_password)
    val invalidPhone = stringResource(id = R.string.invalid_phone)
    val weakPasswordMessage = stringResource(id = R.string.weak_password)
    val matchPasswordMessage = stringResource(id = R.string.password_match)

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF6650a4))
        )
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.wave),
            contentDescription = "wave"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            ScreenTitleText(title = stringResource(id = R.string.register))
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.email_label))
            EmailTextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                isError = emailError != null,
                errorMessage = emailError
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.phone_label))
            PhoneTextField(
                value = phoneValue,
                onValueChange = { phoneValue = it },
                isError = phoneError != null,
                errorMessage = phoneError
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.password_label))
            PasswordTextField(
                value = passwordValue,
                onShowPassword = { showPassword = !showPassword },
                onValueChange = { passwordValue = it },
                showPassword = showPassword,
                placeholder = stringResource(
                    id = R.string.password_hint
                ),
                isError = passwordError != null,
                errorMessage = passwordError
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = stringResource(id = R.string.password_confirm_label))
            PasswordTextField(
                value = passwordConfirmValue,
                onShowPassword = { showConfirmPassword = !showConfirmPassword },
                onValueChange = { passwordConfirmValue = it },
                showPassword = showConfirmPassword,
                placeholder = stringResource(
                    id = R.string.password_confirm_hint
                ),
                isError = passwordConfirmError != null,
                errorMessage = passwordConfirmError
            )
            Spacer(modifier = Modifier.height(SIZE_LARGE))
            Button(
                modifier = Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    // Validate inputs when login button is clicked
                    emailError = if (emailValue.isBlank() || !isValidEmail(emailValue)) {
                        invalidEmailMessage
                    } else {
                        null
                    }

                    phoneError = if (phoneValue.isBlank() || !isValidPhoneNumber(phoneValue)) {
                        invalidPhone
                    } else {
                        null
                    }

                    passwordError = if (passwordValue.isBlank() || !isValidPassword(passwordValue)) {
                            weakPasswordMessage
                        } else {
                            null
                        }

                    passwordConfirmError = if (passwordConfirmValue != passwordValue) {
                        matchPasswordMessage
                    } else {
                        null
                    }

                    if (emailError == null
                        && passwordError == null
                        && passwordConfirmError == null
                        && phoneError == null
                    ) {
                        Toast.makeText(
                            context,
                            "Creando cuenta...",
                            Toast.LENGTH_SHORT
                        ).show()
                        btnSignUpOnClick(emailValue, passwordValue, phoneValue)
                    }
                },
                shape = RoundedCornerShape(SIZE_MEDIUM)
            ) {
                Text(text = stringResource(id = R.string.btn_create_account), fontSize = 18.sp)
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(SIZE_EXTRA_LARGE),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.not_account_signup))
                    TextButton(onClick = { onNavigateToLogin() }) {
                        Text(text = stringResource(id = R.string.btn_login_label))
                    }
                }
            }

        }
    }
}


@Preview(device = Devices.PIXEL_3_XL, showBackground = true)
@Composable
fun SignUpScreenPreview() {
    TestloginTheme {
        SignUp(
            btnSignUpOnClick = { email, password, phone ->

            }, onNavigateToLogin = {})
    }
}
