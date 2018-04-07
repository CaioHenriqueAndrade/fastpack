package com.fastpack.fastpackandroid.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by root on 28/03/18.
 */

abstract class ActivityBasic : AppCompatActivity(), Interfaces.ActivityBasicMethods {

    private var isVisibl = false
    private var alreadyDestroyed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        isVisibl = true
        super.onCreate(savedInstanceState)
        setContentView(idLayout)
    }

    fun setActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        layoutBasic.init( findViewById(idContainerView) )
    }

    override fun onResume() {
        isVisibl = true
        super.onResume()
    }


    override fun onStop() {
        isVisibl = false
        super.onStop()
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun isAlreadyDestroyed(): Boolean {
        return alreadyDestroyed
    }

    override fun isVisible(): Boolean {
        return isVisibl
    }

}
