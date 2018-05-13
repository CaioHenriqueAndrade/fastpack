package com.fastpack.fastpackandroid.activity

import android.content.Intent
import android.os.Bundle

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutMainActivity

class MainActivity : ActivityBasic() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity( Intent(this,ActivityPedidoCriar::class.java) )
        setActionBar()
    }

    override fun getIdLayout(): Int {
        return R.layout.activity_main
    }

    override fun getNewInstanceOfLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return LayoutMainActivity(this)
    }

    override fun getIdContainerView(): Int {
        return R.id.container
    }

}
