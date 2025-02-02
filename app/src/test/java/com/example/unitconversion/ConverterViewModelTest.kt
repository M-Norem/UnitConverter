package com.example.unitconversion

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConverterViewModelTest {

    private lateinit var viewModel: ConverterViewModel

    @Before
    fun setUp() {
        viewModel = ConverterViewModel()
    }

    // Temperature Tests
    @Test
    fun testCelsiusToFahrenheit() {
        val result = viewModel.convertTemperature(0.0, "°C", "°F")
        assertEquals(32.0, result, 0.1)
    }

    @Test
    fun testFahrenheitToCelsius() {
        val result = viewModel.convertTemperature(32.0, "°F", "°C")
        assertEquals(0.0, result, 0.1)
    }

    @Test
    fun testCelsiusToKelvin() {
        val result = viewModel.convertTemperature(100.0, "°C", "K")
        assertEquals(373.15, result, 0.1)
    }

    // Length Tests
    @Test
    fun testMetersToFeet() {
        val result = viewModel.convertLength(1.0, "m", "ft")
        assertEquals(3.281, result, 0.1)
    }

    @Test
    fun testFeetToMeters() {
        val result = viewModel.convertLength(3.281, "ft", "m")
        assertEquals(1.0, result, 0.1)
    }

    // Mass Tests
    @Test
    fun testKilogramsToPounds() {
        val result = viewModel.convertWeight(1.0, "kg", "lb")
        assertEquals(2.205, result, 0.1)
    }

    @Test
    fun testPoundsToKilograms() {
        val result = viewModel.convertWeight(2.205, "lb", "kg")
        assertEquals(1.0, result, 0.1)
    }

    // Edge Case Tests
    @Test
    fun testSameUnitConversion() {
        val result = viewModel.convertTemperature(100.0, "°C", "°C")
        assertEquals(100.0, result, 0.1)
    }

    @Test
    fun testInvalidConversion() {
        val result = viewModel.convertTemperature(100.0, "m", "°C") // Invalid conversion
        assertEquals(100.0, result, 0.1) // Should return the input unchanged
    }
}
