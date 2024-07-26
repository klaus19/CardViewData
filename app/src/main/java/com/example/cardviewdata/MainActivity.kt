
package com.example.cardviewdata
import android.annotation.SuppressLint
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

    private lateinit var textCount: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        textCount = findViewById<TextView>(R.id.textCount)
        val btnGo = findViewById<Button>(R.id.btnGo)

        val cards = listOf("Item1")

        // Load the saved text count
        loadTextCount()

        val adapter = PractiseAdapter(cards,textCount)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnGo.setOnClickListener {
            val intent = Intent(this@MainActivity, Second::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadTextCount()
    }

    fun getTextCount(): Int {
        return textCount.text.toString().toIntOrNull() ?: 0
    }

    fun updateTextCount(newValue: Int) {
        textCount.text = newValue.toString()
        saveTextCount(newValue)
        val intent = Intent("com.example.UPDATE_TEXT_COUNT")
        intent.putExtra("textCount", newValue)
        sendBroadcast(intent)
    }

    private fun saveTextCount(count: Int) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("textCount", count)
        editor.apply()
    }

    private fun loadTextCount() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedCount = sharedPreferences.getInt("textCount", 0)
        textCount.text = savedCount.toString()
    }
}
