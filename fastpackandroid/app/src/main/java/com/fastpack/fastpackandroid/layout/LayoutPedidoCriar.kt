package com.fastpack.fastpackandroid.layout

import android.content.Intent
import android.view.View
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityAddress
import com.fastpack.fastpackandroid.activity.ActivityPedidoCriar
import com.fastpack.fastpackandroid.activity.ActivityPedidoFinalizar

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Address

class LayoutPedidoCriar(methods: Interfaces.ActivityGetter) : LayoutPedidoCriarActionButtons(methods) {

    fun whenPedidoEntregaReceiv(addressEnt : Address) {
        addressEntrega = addressEnt
    }

    fun whenPedidoRetiradaReceiv(ad : Address) {
        addressRetirada = ad

    }
}

abstract class LayoutPedidoCriarActionButtons(methods: Interfaces.ActivityGetter) : LayoutPedidoBasic(methods) {

    var addressRetirada: Address? = null
    var addressEntrega : Address? = null

    override fun whenClickButtonAddressRetirar() {
        startActivityAddress( true )
    }

    override  fun whenClickButtonAddressEntregar() {
        startActivityAddress( false )
    }

    override fun whenClickedButtonFinalizar() {
        if( isValido() ) {
            startActivityPedidoFinalizar()
        }
    }

    /********************
     ** abre a activity address com a identificacao correta
     *
     */
    private fun startActivityAddress(isAddressRetirada : Boolean ) {
        var title : String

        val action : String = if(isAddressRetirada) {
            title = "Endereço de retirada"
            ActivityAddress.ACTION_PEDIDO_RETIRADA
        } else {
            title = "Endereço de entrega"
            ActivityAddress.ACTION_PEDIDO_ENTREGA
        }

        val it = Intent( activity , ActivityAddress::class.java )
        it.putExtra( ActivityAddress.KEY_TITLE_TOOLBAR , title )
        it.putExtra( ActivityAddress.KEY_ACAO , action )
        startActivity( it )

    }

    private fun startActivityPedidoFinalizar() {
        startActivity( getIntentPedFinalizar() )
    }

    private fun getIntentPedFinalizar() : Intent {
        val it = Intent( activity , ActivityPedidoFinalizar::class.java )
        it.putExtra( ActivityPedidoFinalizar.KEY_AD_RETIRA  , addressEntrega!!.toJson() )
        it.putExtra( ActivityPedidoFinalizar.KEY_AD_ENTREGA , addressRetirada!!.toJson() )
        it.putExtra( ActivityPedidoCriar.KEY_MEDIA , activity.intent.getStringExtra( ActivityPedidoCriar.KEY_MEDIA ))
        return it
    }

    private fun isValido(): Boolean {

        if( addressEntrega == null ) {
            makeText("Precisamos do endereço onde será entregue.")
            return false
        }

        if( addressRetirada == null ) {
            makeText("Precisamos do endereço onde será retirado.")
            return false
        }

        return true
    }

}

abstract class LayoutPedidoBasic(methods: Interfaces.ActivityGetter) : LayoutBasic(methods), View.OnClickListener {

    private lateinit var buttonAddressRetirar : View
    private lateinit var buttonAddressEntregar: View
    private lateinit var buttonFinalizar: View

    override fun recuperarReferencias(view: View) {
        buttonAddressRetirar = view.findViewById(R.id.facybutton_address1)
        buttonAddressEntregar= view.findViewById(R.id.facybutton_address2);
        buttonFinalizar      = view.findViewById(R.id.txt_button)
    }
    override fun bindViewHolder() { }

    override fun setOnClick() {
        buttonFinalizar.setOnClickListener( this )
        buttonAddressEntregar.setOnClickListener( this )
        buttonAddressRetirar.setOnClickListener( this )
    }


    override fun onClick(v: View?) {
        if(v!!.id == buttonFinalizar.id) {
            whenClickedButtonFinalizar()
        } else if(buttonAddressEntregar.id == v.id) {
            whenClickButtonAddressEntregar()
        } else if(buttonAddressRetirar.id == v.id) {
            whenClickButtonAddressRetirar()
        } else throw IllegalStateException("not implemented ${v.javaClass.name}")
    }

    abstract fun whenClickButtonAddressRetirar()

    abstract fun whenClickButtonAddressEntregar()

    abstract fun whenClickedButtonFinalizar()


}