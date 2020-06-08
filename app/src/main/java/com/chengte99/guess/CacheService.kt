package com.chengte99.guess

import android.app.IntentService
import android.content.Intent
import android.util.Log

class CacheService : IntentService("CacheService") {
    companion object {
        val ACTION_CACHE_DONE: String = "action_cache_done"
    }
    private val TAG = CacheService::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent: ")
        Thread.sleep(5000)
        val done = Intent()
        done.action = ACTION_CACHE_DONE
        sendBroadcast(done)
    }

}