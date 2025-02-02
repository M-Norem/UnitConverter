package com.example.unitconversion

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        val inputValue: EditText = findViewById(R.id.inputValue)
        val fromUnitSpinner: Spinner = findViewById(R.id.fromUnitSpinner)
        val toUnitSpinner: Spinner = findViewById(R.id.toUnitSpinner)
        val convertButton: Button = findViewById(R.id.convertButton)
        val resultText: TextView = findViewById(R.id.resultText)

        // Define unit mappings with abbreviations
        val unitMap = mapOf(
            "Temperature" to resources.getStringArray(R.array.temperature_units),
            "Mass" to resources.getStringArray(R.array.mass_units),
            "Length" to resources.getStringArray(R.array.length_units)
        )

        // Populate category dropdown
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unitMap.keys.toList())
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        // Populate input unit dropdown with Temperature by default
        val inputUnitsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unitMap["Temperature"]!!)
        inputUnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromUnitSpinner.adapter = inputUnitsAdapter

        // Update input and output unit dropdowns dynamically when category changes
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedCategory = categorySpinner.selectedItem.toString()

                // Populate input unit dropdown based on selected category
                val inputUnitsAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, unitMap[selectedCategory]!!)
                inputUnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                fromUnitSpinner.adapter = inputUnitsAdapter

                // Automatically update output unit dropdown
                updateOutputUnits(fromUnitSpinner.selectedItem.toString(), unitMap, toUnitSpinner)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Ensure output units are filtered whenever input unit changes
        fromUnitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedUnit = fromUnitSpinner.selectedItem.toString()
                updateOutputUnits(selectedUnit, unitMap, toUnitSpinner)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Convert on button click
        convertButton.setOnClickListener {
            val value = inputValue.text.toString().toDoubleOrNull()
            val fromUnit = fromUnitSpinner.selectedItem.toString()
            val toUnit = toUnitSpinner.selectedItem.toString()

            if (value == null) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = when {
                fromUnit in unitMap["Temperature"]!! -> viewModel.convertTemperature(value, fromUnit, toUnit)
                fromUnit in unitMap["Length"]!! -> viewModel.convertLength(value, fromUnit, toUnit)
                fromUnit in unitMap["Mass"]!! -> viewModel.convertWeight(value, fromUnit, toUnit)
                else -> value
            }

            resultText.text = "Converted Value: $result"
        }
    }

    // Function to filter output units based on selected input unit
    private fun updateOutputUnits(selectedUnit: String, unitMap: Map<String, Array<String>>, toUnitSpinner: Spinner) {
        val selectedCategory = unitMap.entries.find { selectedUnit in it.value }?.key ?: "Temperature"
        val outputUnitsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unitMap[selectedCategory]!!)
        outputUnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toUnitSpinner.adapter = outputUnitsAdapter
    }
}
