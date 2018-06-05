package com.fastpack.fastpackandroid.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutPedidoCriar
import com.fastpack.fastpackandroid.objetos.Address
import com.google.gson.Gson

class ActivityPedidoCriar : ActivityBasicService() {
    private val gson = Gson()

    companion object {
        const val KEY_MEDIA = "KM"
    }

    override fun onReceiv(intent: Intent) {
        if( intent.action.equals( ActivityAddress.ACTION_PEDIDO_ENTREGA ) ) {
            (layout as LayoutPedidoCriar).whenPedidoEntregaReceiv( toAddress(intent) )
        } else if(intent.action.equals( ActivityAddress.ACTION_PEDIDO_RETIRADA )) {
            (layout as LayoutPedidoCriar).whenPedidoRetiradaReceiv( toAddress(intent) )
        } else if( intent.action.equals( ActivityPedidoFinalizar.ACTION_PEDIDO_FINALIZED ) ) {
            finish()
        } else throw IllegalStateException("not implemented ${intent.action}")
    }

    fun toAddress(it : Intent) : Address {
        return gson.fromJson( it.getStringExtra( ActivityAddress.KEY_ADDRESS ) , Address::class.java )
    }

    override fun getActions(): Array<String>? {
        return arrayOf( ActivityAddress.ACTION_PEDIDO_ENTREGA , ActivityAddress.ACTION_PEDIDO_RETIRADA,
                ActivityPedidoFinalizar.ACTION_PEDIDO_FINALIZED )
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        val layout = ( layout as LayoutPedidoCriar )

        var jsonAddressEntrega : String? = getJsonByAddress( layout.addressEntrega )

        var jsonAddressRetirada =  getJsonByAddress( layout.addressRetirada )

        outState?.putString( "1" , jsonAddressEntrega )
        outState?.putString( "2" , jsonAddressRetirada)
        outState?.putString( "3" , intent.getStringExtra( KEY_MEDIA ) )

        super.onSaveInstanceState(outState, outPersistentState)
    }

    fun getJsonByAddress(address : Address?) : String? {
        return if(address != null) {
            address.toJson()
        } else {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar()
        setAllActionBar()
        if( savedInstanceState != null ) {
            val layout = ( layout as LayoutPedidoCriar )
            layout.whenPedidoEntregaReceiv( restoreInstanceAddress( savedInstanceState , "1" ) )
            layout.whenPedidoRetiradaReceiv( restoreInstanceAddress( savedInstanceState , "2" ) )
        }
    }

    fun restoreInstanceAddress(savedInstanceState: Bundle, KEY : String) : Address? {

        val text = savedInstanceState.getString( KEY )

        if( text != null ) {
             return fromGson( text )
        }

        return null
    }

    fun fromGson(json : String) : Address {
        return Gson().fromJson( json , Address::class.java )
    }

    override fun getNewInstanceOfLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return LayoutPedidoCriar( this )
    }

    override fun getIdContainerView(): Int {
        return R.id.container
    }

    override fun getIdLayout(): Int {
        return R.layout.activity_pedido_criar
    }
}
