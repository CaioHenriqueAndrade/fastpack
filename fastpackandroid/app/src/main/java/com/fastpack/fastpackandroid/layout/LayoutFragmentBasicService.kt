package com.fastpack.fastpackandroid.layout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import com.fastpack.fastpackandroid.interfaces.Interfaces

import com.fastpack.fastpackandroid.layout_fragment.LayoutFragmentBasic

abstract class LayoutFragmentBasicService(methods : Interfaces.LayoutFragmentBasic) : LayoutFragmentBasic(methods ) {
    var bManager: LocalBroadcastManager? = null

    override fun init(view: View) {
        super.init(view)
        registerBroadcast()
    }

    private fun registerBroadcast() {
        val actions = getActions() ?: return

        bManager = LocalBroadcastManager.getInstance(activity )
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
        bManager?.unregisterReceiver(bReceiver)
    }

    abstract fun onReceiv(intent: Intent)

    abstract fun getActions(): Array<String>?


}
