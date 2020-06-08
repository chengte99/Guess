package com.chengte99.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.func_recycle.view.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

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

        //Fuel
//        fuelGet()
//        fuelPost()
    }

    private fun fuelPost() {
        Fuel.post(
            "http://ctl.5282288.net/appmenu_api/listApm.php",
            listOf("CrlMode" to "appctl", "App" to "gbb", "IsDev" to "1")
        )
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.d(TAG, "Result.Failure: ${ex}")
                    }
                    is Result.Success -> {
                        val data = result.get()
                        Log.d(TAG, "Result.Success: ${data}")
                        val json = JSONObject(data)
                        val app = json.getString("App")
                        Log.d(TAG, "Result.App: ${app}")
                    }
                }
            }
    }

    private fun fuelGet() {
        "https://httpbin.org/get"
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.d(TAG, "Failure.Success: ${ex}")
                    }
                    is Result.Success -> {
                        val data = result.get()
                        Log.d(TAG, "Result.Success: ${data}")
                    }
                }
            }
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
