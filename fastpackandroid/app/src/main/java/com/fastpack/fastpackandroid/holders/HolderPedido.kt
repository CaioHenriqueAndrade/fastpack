package com.fastpack.fastpackandroid.holders

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityPedidoAceitar

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Pedido
import com.google.gson.Gson

class HolderPedido(view: View, methods: Interfaces.AdapterRecyclerMethods) : HolderBasic(view, methods), View.OnClickListener {


    private lateinit var txtTitulo : TextView
    private lateinit var txtStatus : TextView
    private lateinit var txtDescricao : TextView

    override fun recuperarReferencias(view: View) {
        txtTitulo = view.findViewById(R.id.txt_titulo)
        txtStatus = view.findViewById(R.id.txt_status)
        txtDescricao = view.findViewById(R.id.txt_descricao)
    }

    override fun bindViewHolder() {
        val pedido = `object` as Pedido
        txtTitulo.text = pedido.getLayoutPedidoData( resources )
        txtStatus.text = pedido.getStatusLayout( resources )
        txtDescricao.text = pedido.descPedido
    }

    override fun setOnClick() {
        itemView.setOnClickListener( this )
    }

    override fun onClick(v: View?) {
        val it = Intent( activity , ActivityPedidoAceitar::class.java )
        it.putExtra( ActivityPedidoAceitar.KEY_PEDIDO , Gson().toJson(`object` as Pedido) )
        activity.startActivity( it )
    }
}
