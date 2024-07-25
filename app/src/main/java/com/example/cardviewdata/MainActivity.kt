package com.example.cardviewdata

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

     private lateinit var textCount:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        textCount = findViewById<TextView>(R.id.textCount)
        val btnGo = findViewById<Button>(R.id.btnGo)

        val cards = listOf("Item1","Item2","Item3","Item4")


        // Load the saved text count
        loadTextCount()

        val adapter = PractiseAdapter(cards,textCount)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnGo.setOnClickListener {
            val intent = Intent(this@MainActivity,Second::class.java)
            startActivity(intent)
        }

        }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(updateReceiver)
    }

    private fun saveTextCount(count: Int) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("textCount", count)
        editor.apply() // or editor.commit()
    }

    private fun loadTextCount() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedCount = sharedPreferences.getInt("textCount", 0)
        textCount.text = savedCount.toString()
    }

    private fun sendUpdateBroadcast(count: Int) {
        val intent = Intent("com.example.UPDATE_TEXT_COUNT")
        intent.putExtra("textCount", count)
        sendBroadcast(intent)
    }

    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val newCount = intent?.getIntExtra("textCount", 0) ?: 0
            textCount.text = newCount.toString()
        }
    }

    fun updateTextCount(newValue: Int) {
        // Update the TextView
        textCount.text = newValue.toString()
        // Save the new value
        saveTextCount(newValue)
        // Send broadcast
        sendUpdateBroadcast(newValue)
    }
}
