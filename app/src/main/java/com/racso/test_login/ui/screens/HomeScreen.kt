package com.racso.test_login.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.racso.test_login.R
import com.racso.test_login.Screen
import com.racso.test_login.utils.SIZE_MEDIUM
import com.racso.test_login.utils.isValidEmail
import com.racso.test_login.utils.isValidPassword


@Composable
fun HomeScreen(onLogout: () -> Unit, emailArg: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = emailArg)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .width(170.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(SIZE_MEDIUM),
            onClick = { onLogout() }
        ) {
            Text(text = stringResource(id = R.string.logout), fontSize = 18.sp)
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_3_XL)
@Composable
fun HomeScreenPreview() {
    HomeScreen( {}, emailArg = "escamillaluquenoo@gmail.com")
}