package com.msa.calculator

class CalculatorManager {

    // دکمه‌های ویژه مثل AC و +/- و %
    fun onExtraButtonClick(
        input: String,
        display: String,
        resetState: () -> Unit
    ): String {
        return when (input) {
            "AC" -> {
                resetState() // بازنشانی کامل وضعیت
                "0"
            }
            "+/-" -> if (display.startsWith("-")) display.substring(1) else "-$display"
            "%" -> (display.toDoubleOrNull()?.div(100))?.toString() ?: "0"
            else -> display
        }
    }

    // مدیریت عملگرها مثل +، -، *، / و =
    fun onOperatorClick(
        operator: String,
        display: String,
        lastNumber: Double?,
        pendingOperator: String?
    ): String {
        val currentNumber = display.toDoubleOrNull() ?: return display

        if (pendingOperator != null && lastNumber != null) {
            val result = when (pendingOperator) {
                "+" -> lastNumber + currentNumber
                "-" -> lastNumber - currentNumber
                "*" -> lastNumber * currentNumber
                "/" -> if (currentNumber != 0.0) lastNumber / currentNumber else 0.0
                else -> currentNumber
            }
            return result.toString()
        }

        return display
    }
}

// تابع بازنشانی وضعیت
fun resetState(
    updateDisplay: (String) -> Unit,
    updateLastNumber: (Double?) -> Unit,
    updatePendingOperator: (String?) -> Unit,
    updateClearOnNextDigit: (Boolean) -> Unit
) {
    updateDisplay("0")
    updateLastNumber(null)
    updatePendingOperator(null)
    updateClearOnNextDigit(false)
}



