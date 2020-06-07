package com.chengte99.guess

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chengte99.guess.data.Record
import com.chengte99.guess.data.RecordDatabase

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD: Int = 100
    val TAG = MaterialActivity::class.java.simpleName
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            replay()
        }
        counter.setText(secretNumber.count.toString())
        Log.d(TAG, "onCreate: ${secretNumber.secret}");

        //Room test
//        Thread({
//            val recordDatabase = RecordDatabase.getInstance(this)
//            recordDatabase?.recordDao()?.insert(Record("kevin", 10))
//        }).start()

//        AsyncTask.execute {
//            val recordDatabase = RecordDatabase.getInstance(this)
//            val list = recordDatabase?.recordDao()?.getAll()
//            list?.forEach {
//                Log.d(TAG, "record: ${it.name} ${it.counter}");
//            }
//        }
    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle("Alert Tip")
            .setMessage("Are you sure to reset?")
            .setPositiveButton("OK", { dialog, which ->
                secretNumber.reset()
                counter.setText(secretNumber.count.toString())
                ed_number.setText("")
            })
            .setNeutralButton("Cancel", null)
            .show()
    }

    fun check(view: View) {
        var num = ed_number.text.toString().toInt()
        val diff = secretNumber.validate(num)
        var message = ""
        if (diff < 0) {
            message = "Bigger"
        }else if (diff > 0) {
            message = "Smaller"
        }else {
            message = "You got it"
        }
        AlertDialog.Builder(this)
            .setTitle("Alert Tip")
            .setMessage(message)
            .setPositiveButton("OK", {dialog, which ->
                if (diff == 0){
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER", secretNumber.count)
                    startActivityForResult(intent, REQUEST_RECORD)
//                    startActivity(intent)
                }
            })
            .show()
        counter.setText(secretNumber.count.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                val nickname = data?.getStringExtra("NICKNAME")
                Log.d(TAG, "onActivityResult: ${nickname}")
                replay()
            }
        }
    }
}
