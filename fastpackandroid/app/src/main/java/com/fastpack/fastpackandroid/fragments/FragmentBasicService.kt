package com.fastpack.fastpackandroid.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.fastpack.fastpackandroid.interfaces.Interfaces

abstract class FragmentBasicService : FragmentBasic() {

        var bManager: LocalBroadcastManager? = null


        init {
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
            super.onDestroy()
            bManager?.unregisterReceiver(bReceiver);
        }

        abstract fun onReceiv(intent: Intent)

        abstract fun getActions(): Array<String>?

}
