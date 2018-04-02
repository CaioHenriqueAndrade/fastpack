package com.fastpack.fastpackandroid.adapters

import android.support.v4.app.Fragment

import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by root on 02/04/18.
 */

class AdapterTabbed(adapterTabbed: Interfaces.AdapterTabbed) : AdapterTabbedBasic(adapterTabbed) {



    companion object {
        const val NUMBER_TABS = 3
    }


    override fun getItem(position: Int): Fragment? {
        return null
    }

    override fun getCount(): Int {
        return NUMBER_TABS
    }
}
