package com.fastpack.fastpackandroid.layout

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.TextView
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityPedidoAceitar

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.model.ModelPedido
import com.fastpack.fastpackandroid.objetos.Pedido
import com.fastpack.fastpackandroid.objetos.Usuario
import com.google.gson.Gson


class LayoutPedidoAceitar(methods: Interfaces.ActivityGetter) : LayoutPedidoAceitarBasic(methods), Interfaces.ModelUtils {

    lateinit var pedido : Pedido

    val modelPedido = ModelPedido( this )

    override fun bindViewHolder() {
        objectToLayout( pedido )
    }

    fun getUsuario() : Usuario {
        return LayoutMainActivity.getUsuario( activity )
    }

    fun atualizarPedido() {
        modelPedido.searchAtualizacao( pedido , getIdSearch() )
    }

    private fun getIdSearch(): Int {
        return getUsuario().id
    }

    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        when( param ) {
            ModelPedido.PARAM_ATUALIZAR -> {
                activity.runOnUiThread({ whenRecebidoAtualizacao( responseCode , `object` ) })
            }
        }
    }

    private fun whenRecebidoAtualizacao(responseCode: Int, obj: Any?) {
        if( responseCode == 200 && obj != null ) {
            atualizarPedido( obj as Pedido )
        }
    }

    private fun atualizarPedido( newPedido : Pedido ) {
        if( pedido.status != newPedido.status ) {
            pedido.atualizar( newPedido )
            notifyStatusChanged( newPedido )
            sendBroadcast( newPedido )
        }
    }

    private fun sendBroadcast(newPedido: Any) {
        val it = Intent()
        it.action = ActivityPedidoAceitar.ACTION_PEDIDO_ATUALIZED
        it.putExtra( ActivityPedidoAceitar.KEY_PEDIDO , Gson().toJson( newPedido ) )
        LocalBroadcastManager.getInstance( activity ).sendBroadcast( it )
    }

}


abstract class LayoutPedidoAceitarBasic(methods: Interfaces.ActivityGetter) : LayoutBasic(methods) {

    private lateinit var txtAddressEntrega  : TextView
    private lateinit var txtAddressRetirada : TextView
    private lateinit var txtComentarios     : TextView
    private lateinit var txtStatus : TextView
    private lateinit var txtPreco  : TextView
    private lateinit var txtButton : TextView

    override fun recuperarReferencias(view: View) {
        txtAddressRetirada = view.findViewById(R.id.txt_2 )
        txtAddressEntrega = view.findViewById( R.id.txt_4 )
        txtComentarios      = view.findViewById( R.id.txt_6)
        txtPreco            = view.findViewById(R.id.txt_7)
        txtButton           = view.findViewById(R.id.txt_bottom)
        txtStatus           = view.findViewById(R.id.txt_0)
    }

    protected fun objectToLayout(pedido : Pedido) {
        txtAddressEntrega.text = pedido.addressEntrega.format()
        txtAddressRetirada.text = pedido.addressRetirada.format()
        txtComentarios.text = pedido.descPedido
        txtPreco.text = pedido.getLayoutPreco( resources )
        txtStatus.text = pedido.getStatusLayout( resources )
    }

    fun notifyStatusChanged(pedido : Pedido) {
        txtStatus.text = pedido.getStatusLayout( resources )
    }
}

