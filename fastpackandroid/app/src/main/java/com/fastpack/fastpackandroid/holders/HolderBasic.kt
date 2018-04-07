package com.fastpack.fastpackandroid.holders

import android.app.Activity
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View

import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by Caio on 07/04/2018.
 */

abstract class HolderBasic(view: View, val methods: Interfaces.AdapterRecyclerMethods) : RecyclerView.ViewHolder(view), Interfaces.Holder {

    override fun init(view: View) {
        recuperarReferencias(view)
        setOnClick()
    }

    override fun recuperarReferencias(view: View) {}

    override fun iniciarDadosNoLayout() {

    }

    override fun setOnClick() {

    }

    override fun getString(idRes: Int): String {
        return resources.getString(idRes)
    }

    override fun getResources(): Resources {
        return activity.resources
    }

    override fun getObject(): Any {
        return methods.getObjectByPosition(adapterPosition)
    }

    override fun getActivity(): Activity {
        return methods.activity
    }

}
