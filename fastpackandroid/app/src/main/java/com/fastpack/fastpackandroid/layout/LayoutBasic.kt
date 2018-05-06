package com.fastpack.fastpackandroid.layout

import android.app.Activity
import android.content.res.Resources
import android.support.annotation.StringRes
import android.view.View
import android.widget.Toast

import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by root on 28/03/18.
 */

abstract class LayoutBasic(protected val methods : Interfaces.ActivityGetter ) : Interfaces.LayoutMethodsRequierieds {



    override fun setOnClick() {}


    override fun init(view: View) {
        recuperarReferencias(view)
        bindViewHolder()
        setOnClick()
    }

    override fun onResume() { }
    override fun onStop() { }

    fun makeText(text : String) {
        Toast.makeText( activity , text , Toast.LENGTH_LONG ).show()
    }
    override fun getString(@StringRes idRes: Int): String {
        return resources.getString( idRes )
    }

    override fun getResources(): Resources {
        return methods.activity.resources
    }

    override fun getActivity(): Activity {
        return methods.activity
    }

}
