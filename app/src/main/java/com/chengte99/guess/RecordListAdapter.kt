package com.chengte99.guess

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chengte99.guess.data.Record
import kotlinx.android.synthetic.main.list_recycle.view.*

class RecordListAdapter(var records: List<Record>): RecyclerView.Adapter<RecordListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordListViewHolder {
        return RecordListViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_recycle, parent, false))
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: RecordListViewHolder, position: Int) {
        holder.nameText.text = records.get(position).name
        holder.countText.text = records.get(position).counter.toString()
    }
}

class RecordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameText = itemView.record_name
    val countText = itemView.record_count
}