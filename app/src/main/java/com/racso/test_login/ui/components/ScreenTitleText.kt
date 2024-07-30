package com.racso.test_login.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.racso.test_login.utils.SIZE_EXTRA_SMALL


@Composable
fun ScreenTitleText(title: String){
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF6650a4)
        )
        Spacer(modifier = Modifier.height(SIZE_EXTRA_SMALL))
        Divider(
            modifier = Modifier
                .width(55.dp),
            color = Color(0xFF6650a4),
            thickness = 1.dp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ScreenTitleTextPreview(){
    ScreenTitleText("Ingreso")
}