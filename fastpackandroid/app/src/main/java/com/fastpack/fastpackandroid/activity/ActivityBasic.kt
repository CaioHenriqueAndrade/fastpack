package com.fastpack.fastpackandroid.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by root on 28/03/18.
 */

abstract class ActivityBasic : AppCompatActivity(), Interfaces.ActivityBasicMethods {

    private var isVisibl = false
    private var alreadyDestroyed = false

    protected lateinit var layout: Interfaces.LayoutMethodsRequierieds

    override fun onCreate(savedInstanceState: Bundle?) {
        isVisibl = true
        super.onCreate(savedInstanceState)
        setContentView(idLayout)
    }

    fun setActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    fun setAllActionBar() {
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        getSupportActionBar()!!.setHomeButtonEnabled(true)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        layout = newInstanceOfLayoutBasic
        layout.init(findViewById(idContainerView))
    }

    override fun onResume() {
        isVisibl = true
        super.onResume()
        layout.onResume()
    }


    override fun onStop() {
        isVisibl = false
        super.onStop()
        layout.onStop()
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
