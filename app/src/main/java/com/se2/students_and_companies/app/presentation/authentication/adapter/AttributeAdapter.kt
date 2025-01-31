package com.se2.students_and_companies.app.presentation.authentication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.se2.students_and_companies.R

class AttributeAdapter(private val items: MutableList<String>, private val onDelete: (Int) -> Unit) :
    RecyclerView.Adapter<AttributeAdapter.TextViewHolder>() {

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.attribute_item, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val text = items[position]
        holder.textView.text = text

        // Handle delete button click
        holder.textView.setOnClickListener {
            onDelete(position) // Call the delete function passed from MainActivity
        }
    }

    override fun getItemCount(): Int = items.size
}
