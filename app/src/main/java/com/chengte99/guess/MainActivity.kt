package com.chengte99.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.func_recycle.view.*

class MainActivity : AppCompatActivity() {
    val functions = listOf<String>(
        "Camera",
        "Game",
        "Record list",
        "Download",
        "Maps",
        "News")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = FunctionAdapter()
    }

    inner class FunctionAdapter: RecyclerView.Adapter<FunctionViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionViewHolder {
            val view: View = layoutInflater.inflate(R.layout.func_recycle,
                parent, false)
            val holder = FunctionViewHolder(view)
            return holder
        }

        override fun getItemCount(): Int {
            return functions.size
        }

        override fun onBindViewHolder(holder: FunctionViewHolder, position: Int) {
            holder.nameText.setText(functions.get(position))
            holder.itemView.setOnClickListener {
                testFunc(position)
            }
        }

    }

    private fun testFunc(position: Int) {
        when(position) {
            1 -> startActivity(Intent(this, MaterialActivity::class.java))
            2 -> startActivity(Intent(this, RecordListActivity::class.java))
            else -> return
        }
    }
}

class FunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameText: TextView = itemView.func_name
}
