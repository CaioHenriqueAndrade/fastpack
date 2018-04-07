package com.fastpack.fastpackandroid.adapter_recycler

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.holders.HolderEntregador
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.UsuarioPrestador

/**
 * Created by Caio on 07/04/2018.
 */

class AdapterRecyclerEntregadores(ac: Interfaces.ActivityGetter) : AdapterRecyclerBasic<UsuarioPrestador>(ac) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HolderEntregador(layoutInflater.inflate(R.layout.layout_3txt_circleimageview, parent, false), this)
    }

    override fun getItemCount(): Int {
        if( list == null ) return 0

        return list!!.size
    }
}
