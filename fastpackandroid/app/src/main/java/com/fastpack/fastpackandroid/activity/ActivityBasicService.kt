package com.fastpack.fastpackandroid.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager

abstract class ActivityBasicService : ActivityBasic() {
    var bManager: LocalBroadcastManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBroadcast()
    }

    private fun registerBroadcast() {
        val actions = getActions() ?: return

        bManager = LocalBroadcastManager.getInstance(this)
        val intentFilter = IntentFilter()

        for (action in actions) {
            intentFilter.addAction(action)
        }

        bManager!!.registerReceiver(bReceiver, intentFilter)
    }

    private val bReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onReceiv(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        bManager?.unregisterReceiver(bReceiver);
    }

    abstract fun onReceiv(intent: Intent)

    abstract fun getActions(): Array<String>?
}
