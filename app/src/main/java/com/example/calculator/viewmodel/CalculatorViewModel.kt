package com.example.calculator.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculator.CalculatorState

class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

}