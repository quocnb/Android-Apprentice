package com.quocnb.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val taskTextView: TextView = itemView.findViewById(R.id.VH02_textView_task)
}