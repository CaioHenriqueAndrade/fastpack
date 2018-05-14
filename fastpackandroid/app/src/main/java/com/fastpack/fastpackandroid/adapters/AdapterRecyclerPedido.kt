package com.fastpack.fastpackandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.adapter_recycler.AdapterRecyclerBasic
import com.fastpack.fastpackandroid.holders.HolderNoPedido
import com.fastpack.fastpackandroid.holders.HolderPedido
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Pedido

class AdapterRecyclerPedido(ac: Interfaces.ActivityGetter) : AdapterRecyclerBasic<Pedido>(ac) {

    companion object {
        const val TYPE_NO_DADOS = 4
        const val TYPE_PEDIDO   = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when( viewType ) {

            TYPE_NO_DADOS -> HolderNoPedido( inflate( R.layout.layout_imageview_msg , parent ) , this )

            TYPE_PEDIDO -> HolderPedido( inflate(R.layout.layout_pedido , parent ) , this )

            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        if( list == null ) return 1

        if(list!!.size == 0) return 1

        return list!!.size
    }


    override fun getItemViewType(position: Int): Int {
        if( list == null ) return TYPE_LOADING

        if( list!!.size == 0 ) return TYPE_NO_DADOS

        return TYPE_PEDIDO
    }


    fun notifyItemChanged(p : Pedido) {
        for ( (index , pedido) in list!!.withIndex() ) {
            if( pedido.id == p.id ) {
                pedido.atualizar( p )
                notifyItemChanged( index )
                break
            }
        }
}

}
