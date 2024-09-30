package com.msa.calculator

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




// دکمه‌های ویژه مثل AC، +/- و %
@Composable
fun extraButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (String) -> Unit
) {
    flatButton(
        modifier = modifier,
        text = text,
        backgroundColor = Color.DarkGray,
        onClick = onClick
    )
}

// دکمه‌های عددی
@Composable
fun digitButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (String) -> Unit
) {
    flatButton(
        modifier = modifier,
        text = text,
        backgroundColor = Color.Gray,
        onClick = onClick
    )
}

// دکمه‌های عملگر مثل +، -، *، /، =
@Composable
fun operatorButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (String) -> Unit
) {
    flatButton(
        modifier = modifier,
        text = text,
        backgroundColor = Color(0xffff4d00), // رنگ نارنجی
        onClick = onClick
    )
}

// قالب اصلی دکمه‌ها
@Composable
fun flatButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    contentColor: Color = Color.White,
    onClick: (String) -> Unit
) {
    Button(
        modifier = modifier
            .heightIn(80.dp)
            .padding(1.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
        ),
        onClick = { onClick(text) }
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 30.sp)
        )
    }
}
