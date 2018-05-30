package com.fastpack.fastpackandroid.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutPedidoAceitar
import com.fastpack.fastpackandroid.objetos.Pedido
import com.google.gson.Gson

class ActivityPedidoAceitar : ActivityBasic() {


    companion object {
        const val KEY_PEDIDO = "P_A"
        const val ACTION_PEDIDO_CHANGED = "AC_PED_CHANGED"
        const val EXTRA_PEDIDO_CHANGED = "E_P_C"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar()
        setAllActionBar()
    }

    override fun getNewInstanceOfLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        val layout = LayoutPedidoAceitar( this )
        layout.pedido = getPedido()
        return layout
    }

    private fun getPedido(): Pedido {
        return Gson().fromJson(intent.getStringExtra( KEY_PEDIDO ), Pedido::class.java )
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putString( KEY_PEDIDO , intent.getStringExtra( KEY_PEDIDO ) )
        super.onSaveInstanceState(outState, outPersistentState)
    }
    override fun getIdContainerView(): Int {
        return R.id.container
    }

    override fun getIdLayout(): Int {
        return R.layout.activity_pedido_aceitar
    }
}
