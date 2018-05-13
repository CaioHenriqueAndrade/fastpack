package com.fastpack.fastpackandroid.layout

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.View

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityBasic
import com.fastpack.fastpackandroid.adapters.AdapterTabbed
import com.fastpack.fastpackandroid.base_dados.BancoManager
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Usuario

/**
 * Created by root on 28/03/18.
 */

class LayoutMainActivity( m : Interfaces.ActivityGetter ) : LayoutMainBasic( m ){



    private val adapterTabbed = AdapterTabbed( this )

    override fun bindViewHolder() {
        adapterTabbed.init()
    }

    companion object {
        private var user : Usuario? = null

        @Synchronized
        fun getUsuario(context : Context): Usuario {
            if( user == null ) {
                user = Usuario()
                user!!.buscar( BancoManager( context ) , null )
            }
            return user!!
        }
    }


}

abstract class LayoutMainBasic( m : Interfaces.ActivityGetter ) : LayoutBasic( m ), Interfaces.AdapterTabbed {

    protected lateinit var tab     : TabLayout
    protected lateinit var viewPag : ViewPager


    override fun recuperarReferencias(view: View?) {
        tab = view!!.findViewById(R.id.tabs)
        viewPag = view.findViewById(R.id.containerPager)
    }

    override fun getSupportV4FragmentManager(): FragmentManager {
        return (activity as ActivityBasic).supportFragmentManager
    }

    override fun getTabLayout(): TabLayout {
        return tab
    }

    override fun getViewPager(): ViewPager {
        return viewPag
    }

}
