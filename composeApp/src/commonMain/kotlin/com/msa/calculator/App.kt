package com.msa.calculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import calculator.composeapp.generated.resources.Res
import calculator.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val buttons = remember {
        listOf(
            listOf("AC", "+/-", "%", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "="),
        )
    }

    var display by remember { mutableStateOf("0") }
    var lastNumber by remember { mutableStateOf<Double?>(null) }
    var pendingOperator by remember { mutableStateOf<String?>(null) }
    var clearOnNextDigit by remember { mutableStateOf(false) }
    val calculator = remember { CalculatorManager() }

    MaterialTheme {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = display,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .padding(10.dp),
                textAlign = TextAlign.End,
                color = Color.White,
                style = TextStyle(fontSize = 45.sp)
            )

            buttons.forEach { rowButtons ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowButtons.forEach { buttonLabel ->
                        when {
                            buttonLabel == "AC" -> {
                                extraButton(
                                    modifier = Modifier.weight(1f),
                                    text = buttonLabel,
                                    onClick = {
                                        resetState(
                                            updateDisplay = { display = it },
                                            updateLastNumber = { lastNumber = it },
                                            updatePendingOperator = { pendingOperator = it },
                                            updateClearOnNextDigit = { clearOnNextDigit = it }
                                        )
                                    }
                                )
                            }
                            buttonLabel == "+/-" -> {
                                extraButton(
                                    modifier = Modifier.weight(1f),
                                    text = buttonLabel,
                                    onClick = {
                                        display = if (display.startsWith("-")) {
                                            display.substring(1) // معکوس کردن عدد
                                        } else {
                                            "-$display" // اضافه کردن علامت منفی
                                        }
                                    }
                                )
                            }
                            buttonLabel == "%" -> {
                                extraButton(
                                    modifier = Modifier.weight(1f),
                                    text = buttonLabel,
                                    onClick = {
                                        display = (display.toDoubleOrNull()?.div(100))?.toString() ?: "0" // محاسبه درصد
                                    }
                                )
                            }
                            buttonLabel in listOf("/", "*", "-", "+", "=") -> {
                                operatorButton(
                                    modifier = Modifier.weight(1f),
                                    text = buttonLabel,
                                    onClick = {
                                        display = calculator.onOperatorClick(
                                            buttonLabel, display, lastNumber, pendingOperator
                                        )
                                        if (buttonLabel != "=") {
                                            pendingOperator = buttonLabel
                                            lastNumber = display.toDoubleOrNull()
                                            clearOnNextDigit = true
                                        } else {
                                            pendingOperator = null
                                            lastNumber = null
                                        }
                                    }
                                )
                            }
                            else -> {
                                digitButton(
                                    modifier = Modifier.weight(
                                        if (rowButtons.size < 4 && buttonLabel == "0") 2f else 1f
                                    ),
                                    text = buttonLabel,
                                    onClick = {
                                        display = if (clearOnNextDigit) {
                                            clearOnNextDigit = false
                                            buttonLabel
                                        } else {
                                            if (display == "0") buttonLabel else display + buttonLabel
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}