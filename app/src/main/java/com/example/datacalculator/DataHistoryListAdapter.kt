package com.example.datacalculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DataHistoryListAdapter(private val context: Context, private val items: List<DataHistoryModel>) :
    RecyclerView.Adapter<DataHistoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.data_history_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.date.text = item.date.toString()
        holder.dataInBytes.text = item.byte.toString()
        holder.dataInKB.text = item.kiloByte.toString()
        holder.dataInMB.text = item.megaByte.toString()
        holder.dataInGB.text = item.gigaByte.toString()
        holder.from.text = item.from.toString()
        holder.to.text = item.to.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataInKB: TextView = itemView.findViewById(R.id.data_in_KB)
        val dataInMB: TextView = itemView.findViewById(R.id.data_in_MB)
        val dataInGB: TextView = itemView.findViewById(R.id.data_in_GB)
        val dataInBytes: TextView = itemView.findViewById(R.id.data_in_bytes)
        val date: TextView = itemView.findViewById(R.id.date)
        val from: TextView = itemView.findViewById(R.id.text_view_duration_from)
        val to: TextView = itemView.findViewById(R.id.text_view_duration_to)
    }
}

