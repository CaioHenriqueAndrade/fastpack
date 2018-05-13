package com.fastpack.fastpackandroid.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutActivityAddress

class ActivityAddress : ActivityBasic() {
    private val layoutActivityAddress = LayoutActivityAddress( this )

    companion object {
        const val KEY_ACAO = "ke34"

        const val ACTION_LOGIN = "ACTION_LOGIN"
        const val ACTION_PEDIDO_RETIRADA = "ACTION_P_R"
        const val ACTION_PEDIDO_ENTREGA = "ACTION_P_E"


        const val KEY_ADDRESS = "KEY_ADDRESS"

        const val KEY_TITLE_TOOLBAR = "KTTB"

    }

    override fun getNewInstanceOfLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return layoutActivityAddress
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar( getToolbar() )
        setAllActionBar()
        setAction()
    }

    private fun getToolbar(): Toolbar {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_address)
        val title = getTitleToolbar()
        if( title != null ) {
            toolbar.title = title
        }
        return toolbar
    }

    fun getTitleToolbar() : String? {
        return intent.getStringExtra( KEY_TITLE_TOOLBAR )
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString( KEY_ACAO , layoutActivityAddress.action )
        outState?.putString( KEY_TITLE_TOOLBAR , getTitleToolbar() )

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

