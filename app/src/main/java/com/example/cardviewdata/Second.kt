package com.example.cardviewdata

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Second : AppCompatActivity() {

    private lateinit var textSecondCount: TextView
    private lateinit var buttonSecond: Button
    private lateinit var recycler1: RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second)

        textSecondCount = findViewById(R.id.textSecondCount)
        buttonSecond = findViewById(R.id.buttonBack)
        recycler1 = findViewById(R.id.recycler1)

        loadTextCount()

        buttonSecond.setOnClickListener {
            val newCount = textSecondCount.text.toString().toIntOrNull() ?: 0

            // Save updated count to SharedPreferences
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putInt("textCount", newCount)
                apply()
            }

            // Send broadcast with the updated count
            sendUpdateBroadcast(newCount)

            // Finish the activity and return to MainActivity
            finish()
        }

        val items = listOf("A1")
        val adapter = SecondAdapter(this, items, textSecondCount)
        recycler1.layoutManager = LinearLayoutManager(this)
        recycler1.adapter = adapter
    }

    private fun loadTextCount() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedCount = sharedPreferences.getInt("textCount", 0)
        textSecondCount.text = savedCount.toString()
    }

    fun sendUpdateBroadcast(newCount: Int) {
        val intent = Intent("com.example.UPDATE_TEXT_COUNT")
        intent.putExtra("textCount", newCount)
        sendBroadcast(intent)
    }
}
