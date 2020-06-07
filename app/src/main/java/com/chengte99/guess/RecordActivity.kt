package com.chengte99.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chengte99.guess.data.Record
import com.chengte99.guess.data.RecordDatabase
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecordActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        job = Job()

        val count = intent.getIntExtra("COUNTER", -1)
        counter.setText(count.toString())

        save.setOnClickListener { view ->
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                .edit()
                .putString("NICKNAME", nick.text.toString())
                .putInt("COUNT", count)
                .apply()

            launch {
                RecordDatabase.getInstance(this@RecordActivity)?.recordDao()?.insert(Record(nick.text.toString(), count))
            }

            val intent = Intent()
            intent.putExtra("NICKNAME", nick.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
