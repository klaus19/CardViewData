package com.example.cardviewdata

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class SecondAdapter(
    private val context: Context,
    private val items: List<String>,
    private val textSecondCount: TextView
) : RecyclerView.Adapter<SecondAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.second_adapter, parent, false)
        return CardViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        holder.textViewAdapter.text = holder.textViewAdapter.text

        // Log item value and TextView text
        Log.d("charm", "Item: $item")
        Log.d("charm", "TextView value: ${holder.textViewAdapter.text}")

        holder.cardViewSecond.setOnClickListener {
            // Get the texAdapterCount value for the clicked card
            val textAdapter = holder.textViewAdapter.text.toString()
            val texAdapterCount = textAdapter.toIntOrNull() ?: 0

            // Log the value to check conversion
            Log.d("SecondAdapter", "texAdapterCount: $texAdapterCount")

            showDialog(texAdapterCount)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun showDialog(texAdapterCount: Int) {
        // Debugging: Log the value of texAdapterCount
        Log.d("SecondAdapter", "texAdapterCount: $texAdapterCount")

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_message)

        // Log current textSecondCount value
        val currentCount = textSecondCount.text.toString()
        Log.d("SecondAdapter", "Current textSecondCount: $currentCount")

        // Ensure there's a space between the message and the count
        messageTextView.text = "Do you have apples $texAdapterCount?"

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)

        val dialog = dialogBuilder.create()

        dialogView.findViewById<Button>(R.id.dialog_button_positive).setOnClickListener {
            try {
                val currentCountInt = currentCount.toInt()
                if (currentCountInt >= texAdapterCount) {
                    // Update textSecondCount and notify the change
                    val newCount = currentCountInt - texAdapterCount
                    textSecondCount.text = newCount.toString()

                    // Save updated count to SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt("textCount", newCount)
                    editor.apply()

                    // Notify the user
                    showToast("Card unlocked successfully")

                    // Send update broadcast
                    if (context is Second) {
                        (context as Second).sendUpdateBroadcast(newCount)
                    }
                } else {
                    // Notify the user
                    showToast("Not enough points to unlock")
                }

            } catch (e: NumberFormatException) {
                Log.e("SecondAdapter", "Error parsing count: $e")
                showToast("Error processing the count")
            }
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.dialog_button_negative).setOnClickListener {
            // Handle negative button click
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showToast(messsage: String) {
        Toast.makeText(context, messsage, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int = items.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardViewSecond: CardView = itemView.findViewById(R.id.cardViewSecond)
        val textViewAdapter: TextView = itemView.findViewById(R.id.texAdapterCount)
    }
}
