package com.example.cardviewdata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PractiseAdapter(
    private val items:List<String>,
    private val textCount:TextView
):RecyclerView.Adapter<PractiseAdapter.CardViewHolder>() {

    private var count=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item

        // Optionally set up click listeners or other interactions
        holder.cardView.setOnClickListener {
            // Increment the count
            val currentCount = textCount.text.toString().toIntOrNull() ?: 0
            val newCount = currentCount + 1
            (textCount.context as MainActivity).updateTextCount(newCount)
        }
    }

    override fun getItemCount(): Int = items.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView1)
        val textView: TextView = itemView.findViewById(R.id.textName)
    }


}