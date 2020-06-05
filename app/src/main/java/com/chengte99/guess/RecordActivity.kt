package com.chengte99.guess

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val count = intent.getIntExtra("COUNTER", -1)
        counter.setText(count.toString())

        save.setOnClickListener { view ->
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                .edit()
                .putString("NICKNAME", nick.text.toString())
                .putInt("COUNT", count)
                .apply()
        }
    }
}
