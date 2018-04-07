package com.fastpack.fastpackandroid.adapters

import android.support.v4.app.Fragment
import com.fastpack.fastpackandroid.fragments.FragmentEntregadores

import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by root on 02/04/18.
 */

class AdapterTabbed(adapterTabbed: Interfaces.AdapterTabbed) : AdapterTabbedBasic(adapterTabbed) {

    var fragment : FragmentEntregadores? = null
        get() { if (field != null) return field; field = FragmentEntregadores.newInstance(); return field }

    companion object {
        const val NUMBER_TABS = 2
    }


    override fun getItem(position: Int): Fragment? {
        //return fragment
        return FragmentEntregadores.newInstance()
    }

    override fun getCount(): Int {
        return NUMBER_TABS
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "Fragment"
    }
}
