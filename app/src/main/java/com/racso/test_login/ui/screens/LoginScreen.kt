package com.racso.test_login.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import com.racso.test_login.ui.components.ScreenTitleText
import com.racso.test_login.utils.SIZE_EXTRA_LARGE
import com.racso.test_login.utils.SIZE_LARGE
import com.racso.test_login.utils.SIZE_MEDIUM
import com.racso.test_login.utils.isValidEmail
import com.racso.test_login.utils.isValidPassword
import com.racso.test_login.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: (Any?) -> Unit
) {
    val loginResult by authViewModel.loginResult.collectAsState()
    loginResult.onSuccess { user ->
        if (user != null) {
            onLoginSuccess(user.email)
            Toast.makeText(LocalContext.current, "Bienvenido!", Toast.LENGTH_SHORT).show()
        }
    }.onFailure { exception ->
        Toast.makeText(
            LocalContext.current,
            exception.message ?: "No logramos iniciar tu sesión",
            Toast.LENGTH_SHORT
        ).show()
    }


    Login(
        modifier = Modifier,
        onNavigateToSignUp = onNavigateToSignUp,
        btnLoginOnClick = { email, password ->
            authViewModel.login(email, password)
        }
    )

}


@Composable
fun Login(
    modifier: Modifier,
    btnLoginOnClick: (String, String) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var emailValue by rememberSaveable { mutableStateOf("") }
    var passwordValue by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var rememberMe by rememberSaveable { mutableStateOf(false) }

    // State for validation errors
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }

    val invalidEmailMessage = stringResource(id = R.string.invalid_email)
    val invalidPasswordMessage = stringResource(id = R.string.invalid_password)

    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
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
            ScreenTitleText(title = stringResource(id = R.string.ingreso))
            Spacer(modifier = Modifier.height(SIZE_LARGE))
            Text(text = stringResource(id = R.string.email_label))
            EmailTextField(
                value = emailValue,
                onValueChange = {
                    emailValue = it
                    Log.e("emailValue", emailValue)
                },
                isError = emailError != null,
                errorMessage = emailError
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
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = rememberMe, onCheckedChange = {
                        rememberMe = it
                        val messageStatus = if (rememberMe) "activó" else "desactivó"
                        Toast.makeText(
                            context,
                            "Se $messageStatus Recordar.",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                    Text(text = stringResource(id = R.string.remember_me))
                }
                TextButton(onClick = {
                    Toast.makeText(
                        context,
                        "Envía a resetear la contraseña.",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text(text = stringResource(id = R.string.paswword_forget))
                }
            }
            Spacer(modifier = Modifier.height(SIZE_LARGE))
            Button(
                modifier = Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { // Validate inputs when login button is clicked
                    emailError =
                        if (emailValue.isBlank() || !isValidEmail(emailValue)) {
                            invalidEmailMessage
                        } else {
                            null
                        }

                    passwordError = if (emailValue.isBlank() || !isValidPassword(passwordValue)) {
                        invalidPasswordMessage
                    } else {
                        null
                    }
                    if (emailError == null && passwordError == null) {
                        Toast.makeText(
                            context,
                            "Iniciando sesión...",
                            Toast.LENGTH_SHORT
                        ).show()
                        btnLoginOnClick(emailValue, passwordValue)
                    }

                },
                shape = RoundedCornerShape(SIZE_MEDIUM)
            ) {
                Text(text = stringResource(id = R.string.btn_login_label), fontSize = 18.sp)
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(SIZE_EXTRA_LARGE),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.not_account))
                    TextButton(onClick = { onNavigateToSignUp() }) {
                        Text(text = stringResource(id = R.string.btn_signup_label))
                    }
                }
            }

        }
    }
}


@Preview(device = Devices.PIXEL_3_XL, showBackground = true)
@Composable
fun LoginScreenPreview() {
    TestloginTheme {
        Login(modifier = Modifier, onNavigateToSignUp = {}, btnLoginOnClick = { email, password ->

        })
    }
}



