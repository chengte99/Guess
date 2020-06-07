package com.chengte99.guess

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chengte99.guess.data.RecordDatabase
import kotlinx.android.synthetic.main.activity_record_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecordListActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_list)

        job = Job()
        launch {
            val recordList = RecordDatabase.getInstance(this@RecordListActivity)?.recordDao()?.getAll()
            recordList?.let {
                recycler.layoutManager = LinearLayoutManager(this@RecordListActivity)
                recycler.setHasFixedSize(true)
                recycler.adapter = RecordListAdapter(it)
            }
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            val recordList = RecordDatabase.getInstance(this@RecordListActivity)?.recordDao()?.getAll()
//            recordList?.let {
//                runOnUiThread {
//                    recycler.layoutManager = LinearLayoutManager(this@RecordListActivity)
//                    recycler.setHasFixedSize(true)
//                    recycler.adapter = RecordListAdapter(it)
//                }
//            }
//        }

//        AsyncTask.execute {
//            val recordList = RecordDatabase.getInstance(this)?.recordDao()?.getAll()
//            recordList?.let {
//                runOnUiThread {
//                    recycler.layoutManager = LinearLayoutManager(this)
//                    recycler.setHasFixedSize(true)
//                    recycler.adapter = RecordListAdapter(it)
//                }
//            }
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
