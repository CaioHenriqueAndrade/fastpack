package com.fastpack.fastpackandroid.adapter_recycler

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.holders.HolderEntregador
import com.fastpack.fastpackandroid.holders.HolderNoEntregador
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Pedido
import com.fastpack.fastpackandroid.objetos.UsuarioPrestador

/**
 * Created by Caio on 07/04/2018.
 */

class AdapterRecyclerEntregadores(ac: Interfaces.ActivityGetter) : AdapterRecyclerBasic<UsuarioPrestador>(ac) {

    companion object {
        const val TYPE_NO_ENTREGADORES = 1
        const val TYPE_ENTREGADOR = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        return when( viewType ) {

            TYPE_ENTREGADOR -> HolderEntregador(layoutInflater.inflate(R.layout.layout_3txt_circleimageview, parent, false), this)

            TYPE_NO_ENTREGADORES -> HolderNoEntregador( inflate(R.layout.layout_imageview_msg , parent ) , this )

            else -> super.onCreateViewHolder( parent , viewType )
        }

    }

    override fun getItemCount(): Int {
        if( list == null ) return 1

        if(list!!.size == 0) return 1

        return list!!.size
    }

    override fun getItemViewType(position: Int): Int {
        if( list == null ) {
            return TYPE_LOADING
        }

        if( list!!.size == 0 ) {
            return TYPE_NO_ENTREGADORES
        }


        return TYPE_ENTREGADOR
    }


}
