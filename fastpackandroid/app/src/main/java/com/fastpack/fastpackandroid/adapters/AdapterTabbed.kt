package com.fastpack.fastpackandroid.adapters

import android.support.v4.app.Fragment
import com.fastpack.fastpackandroid.fragments.FragmentEntregadores
import com.fastpack.fastpackandroid.fragments.FragmentPedidos

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
        if( position == 0 )
            return FragmentEntregadores.newInstance()
        else
            return FragmentPedidos.newInstance()
    }

    override fun getCount(): Int {
        return NUMBER_TABS
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when( position ) {
            0 -> "Entregadores"
            1 -> "Meus pedidos"
            else -> throw IllegalStateException("not implemente name of tab")
        }
    }
}
