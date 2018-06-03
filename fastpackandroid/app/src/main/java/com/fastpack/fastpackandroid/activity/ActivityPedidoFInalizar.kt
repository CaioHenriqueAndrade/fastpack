package com.fastpack.fastpackandroid.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutPedidoFinalizar
import com.fastpack.fastpackandroid.objetos.Address
import com.fastpack.fastpackandroid.utils.UtilsConvert
import com.google.gson.Gson

class ActivityPedidoFinalizar : ActivityBasic() {

    companion object {
        const val KEY_AD_RETIRA = "KART"
        const val KEY_AD_ENTREGA= "KADE"
        const val ACTION_PEDIDO_FINALIZED = "AC_PEDIDO_FINALIZED"
    }


    override fun getNewInstanceOfLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return LayoutPedidoFinalizar( this )
    }

    override fun getIdContainerView(): Int {
        return R.id.container
    }

    override fun getIdLayout(): Int {
        return R.layout.activity_pedido_finalizar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar()
        setAllActionBar()

        onRestoreInstance( savedInstanceState )
    }

    private fun onRestoreInstance(savedInstanceState: Bundle?) {
        if( savedInstanceState != null ) {
            getLayoutPedidoFinalizar().adEntrega = fromJson( savedInstanceState , KEY_AD_RETIRA )
            getLayoutPedidoFinalizar().adRetirada = fromJson( savedInstanceState , KEY_AD_RETIRA)
            getLayoutPedidoFinalizar().setPrecoMedio( getPrecoCorreto() )
        } else {
            getLayoutPedidoFinalizar().adEntrega = fromJson( intent , KEY_AD_ENTREGA)
            getLayoutPedidoFinalizar().adRetirada = fromJson( intent , KEY_AD_RETIRA)
            getLayoutPedidoFinalizar().setPrecoMedio( getPrecoCorreto() )
        }
    }

    fun getPrecoCorreto() : Int {
        val media = intent.getStringExtra(  ActivityPedidoCriar.KEY_MEDIA  ).toInt()
        return UtilsConvert.getTaxaEntrega( media, getLayoutPedidoFinalizar().adEntrega!!.local , getLayoutPedidoFinalizar().adRetirada!!.local )
    }

    fun fromJson(intent : Intent , key : String ) : Address {
        val text = intent.getStringExtra(key)
        return Gson().fromJson( text , Address::class.java )
    }

    fun fromJson(savedInstanceState: Bundle , key : String ) : Address {
        val text = savedInstanceState.getString(key)
        return Gson().fromJson( text , Address::class.java )
    }

    fun getLayoutPedidoFinalizar() : LayoutPedidoFinalizar{
        return layout as LayoutPedidoFinalizar
    }
}
