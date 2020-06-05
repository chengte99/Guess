package com.chengte99.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    val TAG = MaterialActivity::class.java.simpleName
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            AlertDialog.Builder(this)
                .setTitle("Alert Tip")
                .setMessage("Are you sure to reset?")
                .setPositiveButton("OK", {dialog, which ->
                    secretNumber.reset()
                    counter.setText(secretNumber.count.toString())
                    ed_number.setText("")
                })
                .setNeutralButton("Cancel", null)
                .show()
        }
        counter.setText(secretNumber.count.toString())
        println("print ${secretNumber.secret}")
        Log.d(TAG, "onCreate: ${secretNumber.secret}");
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
                    startActivity(intent)
                }
            })
            .show()
        counter.setText(secretNumber.count.toString())
    }
}
