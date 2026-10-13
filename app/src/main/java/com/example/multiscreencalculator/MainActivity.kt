package com.example.multiscreencalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etNum1: EditText
    private lateinit var etNum2: EditText
    private lateinit var tvResult: TextView
    private val historyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNum1 = findViewById(R.id.etNum1)
        etNum2 = findViewById(R.id.etNum2)
        tvResult = findViewById(R.id.tvResult)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSub = findViewById<Button>(R.id.btnSub)
        val btnMul = findViewById<Button>(R.id.btnMul)
        val btnDiv = findViewById<Button>(R.id.btnDiv)
        val btnViewHistory = findViewById<Button>(R.id.btnViewHistory)

        btnAdd.setOnClickListener { calculate("+") }
        btnSub.setOnClickListener { calculate("-") }
        btnMul.setOnClickListener { calculate("*") }
        btnDiv.setOnClickListener { calculate("/") }

        btnViewHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putStringArrayListExtra("HISTORY_KEY", historyList)
            startActivity(intent)
        }
    }

    private fun calculate(operation: String) {
        val num1Text = etNum1.text.toString().trim()
        val num2Text = etNum2.text.toString().trim()

        if (num1Text.isEmpty() || num2Text.isEmpty()) {
            tvResult.text = "Please enter both numbers"
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = num1Text.toDoubleOrNull()
        val num2 = num2Text.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            tvResult.text = "Please enter valid numbers"
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        if (operation == "/" && num2 == 0.0) {
            tvResult.text = "Cannot divide by zero"
            Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> return
        }

        val formattedNum1 = formatNumber(num1)
        val formattedNum2 = formatNumber(num2)
        val formattedResult = formatNumber(result)

        val calculationString = "$formattedNum1 $operation $formattedNum2 = $formattedResult"
        tvResult.text = calculationString
        historyList.add(calculationString)
    }

    private fun formatNumber(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            String.format(Locale.US, "%.2f", value).trimEnd('0').trimEnd('.')
        }
    }
}