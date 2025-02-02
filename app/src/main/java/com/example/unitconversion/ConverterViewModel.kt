package com.example.unitconversion

import androidx.lifecycle.ViewModel

class ConverterViewModel : ViewModel() {

    fun convertTemperature(value: Double, fromUnit: String, toUnit: String): Double {
        return when {
            fromUnit == "°C" && toUnit == "°F" -> (value * 9/5) + 32
            fromUnit == "°F" && toUnit == "°C" -> (value - 32) * 5/9
            fromUnit == "°C" && toUnit == "K" -> value + 273.15
            fromUnit == "K" && toUnit == "°C" -> value - 273.15
            fromUnit == "°F" && toUnit == "K" -> (value - 32) * 5/9 + 273.15
            fromUnit == "K" && toUnit == "°F" -> (value - 273.15) * 9/5 + 32
            else -> value
        }
    }

    fun convertLength(value: Double, fromUnit: String, toUnit: String): Double {
        return when {
            fromUnit == "m" && toUnit == "ft" -> value * 3.28084
            fromUnit == "ft" && toUnit == "m" -> value / 3.28084
            fromUnit == "cm" && toUnit == "in" -> value / 2.54
            fromUnit == "in" && toUnit == "cm" -> value * 2.54
            else -> value
        }
    }

    fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        return when {
            fromUnit == "kg" && toUnit == "lb" -> value * 2.20462
            fromUnit == "lb" && toUnit == "kg" -> value / 2.20462
            fromUnit == "g" && toUnit == "oz" -> value / 28.3495
            fromUnit == "oz" && toUnit == "g" -> value * 28.3495
            else -> value
        }
    }
}
