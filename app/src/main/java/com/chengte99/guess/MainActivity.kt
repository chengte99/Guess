package com.chengte99.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
                .setPositiveButton("OK", null)
                .show()
    }
}
