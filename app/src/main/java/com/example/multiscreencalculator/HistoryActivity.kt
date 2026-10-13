package com.example.multiscreencalculator

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val tvHistory = findViewById<TextView>(R.id.tvHistory)
        val history = intent.getStringArrayListExtra("HISTORY_KEY")
        tvHistory.text = history?.joinToString("\n") ?: "No history yet"
        if (tvHistory.text.isEmpty()) {
            tvHistory.text = "No history yet"
        }
    }
}
