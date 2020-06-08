package com.chengte99.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val beginTransaction = supportFragmentManager.beginTransaction()
//        beginTransaction.add(R.id.container_news, NewsFragment.getInstance())
//            .commit()
        beginTransaction.add(R.id.container_news, NewsFragment.instacne)
            .commit()
    }
}
