package com.fastpack.fastpackandroid.layout

import android.view.View
import android.widget.TextView
import com.fastpack.fastpackandroid.R

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Pedido

class LayoutPedidoAceitar(methods: Interfaces.ActivityGetter) : LayoutPedidoAceitarBasic(methods) {
    lateinit var pedido : Pedido


    override fun bindViewHolder() {
        objectToLayout( pedido )
    }
}

abstract class LayoutPedidoAceitarBasic(methods: Interfaces.ActivityGetter) : LayoutBasic(methods) {

    private lateinit var txtAddressEntrega  : TextView
    private lateinit var txtAddressRetirada : TextView
    private lateinit var txtComentarios     : TextView
    private lateinit var txtPreco  : TextView
    private lateinit var txtButton : TextView

    override fun recuperarReferencias(view: View) {
        txtAddressRetirada = view.findViewById(R.id.txt_2 )
        txtAddressEntrega = view.findViewById( R.id.txt_4 )
        txtComentarios      = view.findViewById( R.id.txt_6)
        txtPreco            = view.findViewById(R.id.txt_7)
        txtButton           = view.findViewById(R.id.txt_bottom)
    }

    protected fun objectToLayout(pedido : Pedido) {
        txtAddressEntrega.text = pedido.addressEntrega.format()
        txtAddressRetirada.text = pedido.addressRetirada.format()
        txtComentarios.text = pedido.descPedido
        txtPreco.text = pedido.getLayoutPreco( resources )
    }
}

