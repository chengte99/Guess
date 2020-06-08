package com.chengte99.guess

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.func_recycle.view.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_CAMERA: Int = 100
    private val TAG = MainActivity::class.java.simpleName

    val functions = listOf<String>(
        "Camera",
        "Game",
        "Record list",
        "Download",
        "News",
        "Maps")

    val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Log.d(TAG, "onReceive: cache ${it.action}")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(CacheService.ACTION_CACHE_DONE)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = FunctionAdapter()

        //spinner
        val colors = arrayOf("RED", "GREEN", "YELLOW")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

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
            0 -> {
                val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                }else {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
                }
            }
            1 -> startActivity(Intent(this, MaterialActivity::class.java))
            2 -> startActivity(Intent(this, RecordListActivity::class.java))
            4 -> startActivity(Intent(this, NewsActivity::class.java))
            else -> return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto()
            }
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cache) {
            Log.d(TAG, "onOptionsItemSelected: cache")
            val intent = Intent(this, CacheService::class.java)
            startService(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}

class FunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameText: TextView = itemView.func_name
}
