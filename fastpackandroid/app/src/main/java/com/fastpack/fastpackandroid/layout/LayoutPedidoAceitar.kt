package com.fastpack.fastpackandroid.layout

import android.view.View
import android.widget.TextView
import com.fastpack.fastpackandroid.R

import com.fastpack.fastpackandroid.interfaces.Interfaces

class LayoutPedidoAceitar(methods: Interfaces.ActivityGetter) : LayoutPedidoAceitarBasic(methods) {

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
        txtComentarios      = view.findViewById( R.id.txt_6_)
        txtPreco            = view.findViewById(R.id.txt_7)
        txtButton           = view.findViewById(R.id.txt_bottom)


    }

    override fun bindViewHolder() {

    }
}

