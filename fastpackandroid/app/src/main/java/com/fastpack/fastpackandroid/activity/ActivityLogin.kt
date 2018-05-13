package com.fastpack.fastpackandroid.activity

import android.content.Intent
import android.os.Bundle
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutLogin

class ActivityLogin : ActivityBasic() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if( (layout as LayoutLogin).existsUser() ) {
            startActivity( Intent( this , MainActivity::class.java) )
            finish()
        }


    }

    override fun getNewInstanceOfLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return LayoutLogin(this)
    }

    override fun getIdContainerView(): Int {
        return R.id.container
    }

    override fun getIdLayout(): Int {
        return R.layout.activity_login_layout
    }


}
