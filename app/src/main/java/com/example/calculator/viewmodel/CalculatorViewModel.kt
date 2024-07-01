package com.example.calculator.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculator.CalculatorAction
import com.example.calculator.CalculatorOperation
import com.example.calculator.CalculatorState

class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(calculatorAction: CalculatorAction) {
        when (calculatorAction) {
            is CalculatorAction.Number -> {
                enterNumber(calculatorAction.number)
            }
            is CalculatorAction.Clear -> {
                state = CalculatorState()
            }
            is CalculatorAction.Delete -> {
                performDeletion()
            }
            is CalculatorAction.Decimal -> {
                enterDecimal()
            }
            is CalculatorAction.Operation -> {
                enterOperation(calculatorAction.calculatorOperation)
            }
            is CalculatorAction.Calculate -> {
                performCalculation()
            }
        }
    }

    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> {
                state = state.copy(
                    number2 = state.number2.dropLast(1)
                )
            }
            state.calculatorOperation != null -> {
                state = state.copy(
                    calculatorOperation = null
                )
            }
            state.number1.isNotBlank() -> {
                state = state.copy(
                    number1 = state.number1.dropLast(1)
                )
            }
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (state.calculatorOperation) {
                is CalculatorOperation.Add -> {
                    number1 + number2
                }
                is CalculatorOperation.Divide -> {
                    number1/number2
                }
                is CalculatorOperation.Subtract -> {
                    number1 - number2
                }
                is CalculatorOperation.Multiply -> {
                    number1*number2
                }
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                calculatorOperation = null
            )
        }
    }

    private fun enterOperation(calculatorOperation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(
                calculatorOperation = calculatorOperation
            )
        }
    }

    private fun enterDecimal() {
        if (state.calculatorOperation != null
            && state.number1.contains(".").not()
            && state.number1.isNotBlank()) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }
        if (state.number2.contains(".").not()
            && state.number2.isNotBlank()) {
            state = state.copy(
                number2 = state.number2 + "."
            )
        }
    }

    private fun enterNumber(number: Int) {
        if (state.calculatorOperation == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        if (state.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }

    companion object {
        const val MAX_NUM_LENGTH = 8
    }
}