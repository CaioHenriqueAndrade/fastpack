package com.fastpack.fastpackandroid.fragments

import android.content.Intent
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityPedidoAceitar
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout_fragment.LayoutFragmentPedidos
import com.fastpack.fastpackandroid.objetos.Pedido
import com.google.gson.Gson

class FragmentPedidos : FragmentBasicService() {


    companion object {

        fun newInstance(): FragmentPedidos {
            return FragmentPedidos()
        }

    }
    override fun getIdLayout(): Int {
        return R.layout.layout_fragment_pedidos
    }

    override fun getNewInstanceLayout(): Interfaces.LayoutFragmentBasicMethods {
        return LayoutFragmentPedidos( this )
    }

    override fun onReceiv(intent: Intent) {
        if( intent.action.equals( ActivityPedidoAceitar.ACTION_PEDIDO_ATUALIZED ) ) {
            (layout as LayoutFragmentPedidos).whenActionPedidoAtualized(
                    Gson().fromJson( intent.getStringExtra( ActivityPedidoAceitar.KEY_PEDIDO ) ,
                            Pedido::class.java )
            )
        } else {
            throw IllegalStateException("not implemented ${intent.action}")
        }
    }

    override fun getActions(): Array<String>? {
        return arrayOf( ActivityPedidoAceitar.ACTION_PEDIDO_ATUALIZED )
    }

    
}