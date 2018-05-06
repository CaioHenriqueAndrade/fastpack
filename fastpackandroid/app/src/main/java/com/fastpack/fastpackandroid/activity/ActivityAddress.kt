package com.fastpack.fastpackandroid.activity

import android.os.Bundle
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutActivityAddress

class ActivityAddress : ActivityBasic() {
    private val layoutActivityAddress = LayoutActivityAddress( this )

    companion object {
        const val KEY_ACAO = "ke34"

        const val ACTION_LOGIN = "ACTION_LOGIN"
        const val KEY_ADDRESS = "KEY_ADDRESS"
    }

    override fun getLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return layoutActivityAddress
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar( findViewById(R.id.toolbar_address) )
        setAllActionBar()
        setAction()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString( KEY_ACAO , layoutActivityAddress.action )
        super.onSaveInstanceState(outState)
    }

    private fun setAction() {
        layoutActivityAddress.action = intent.getStringExtra( KEY_ACAO )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        layoutActivityAddress.onRequestPermissionsResult( requestCode , permissions , grantResults )
    }


    override fun getIdContainerView(): Int {
        return R.id.container
    }

    override fun getIdLayout(): Int {
        return R.layout.activity_layout_address
    }
}

