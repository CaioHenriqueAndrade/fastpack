package com.fastpack.fastpackandroid.layout

import android.content.Intent
import android.support.annotation.WorkerThread
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityPedidoFinalizar

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.model.ModelPedido
import com.fastpack.fastpackandroid.objetos.Address
import com.fastpack.fastpackandroid.objetos.Pedido
import com.fastpack.fastpackandroid.objetos.Usuario
import com.fastpack.fastpackandroid.utils.UtilsConvert
import com.fastpack.fastpackandroid.utils.UtilsProgressDialog
import com.fastpack.fastpackandroid.utils.UtilsValidate

class LayoutPedidoFinalizar(methods: Interfaces.ActivityGetter) : LayoutPedidoFinalizarModel(methods) {

    var adRetirada : Address? = null
    var adEntrega : Address? = null

    override fun getAddressEntrega(): Address {
        return adEntrega!!
    }

    override fun getAddressRetirada(): Address {
        return adRetirada!!
    }

}

abstract class LayoutPedidoFinalizarModel(methods: Interfaces.ActivityGetter) : LayoutPedidoFinalizarBasic(methods), Interfaces.ModelUtils {

    val modelPedido = ModelPedido( this )
    var pedid: Pedido? = null

    protected val utilsProgressDialog = UtilsProgressDialog( activity )

    override fun whenClickConfirmar() {
        if( isValido() ) {
            openProgressDialog()
            mountPedido()
            modelPedido.insertPedido( getPedido() )
        }

    }


    private fun openProgressDialog() {
        utilsProgressDialog.init("Enviando ao servidor...")
        utilsProgressDialog.setIndeterminate()
        utilsProgressDialog.show()
    }

    private fun dismissDialog() {
        utilsProgressDialog.dismiss()
    }

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        if( param ==  ModelPedido.PARAM_INSERIR) {
            activity.runOnUiThread({ whenFinishSendService( responseCode ) })
        }
    }

    private fun whenFinishSendService(responseCode: Int) {
        dismissDialog()
        if(responseCode == 200) {
            sendBroadcastNewPedido()
            makeText("Salvo com sucesso!")
            activity.finish()
        } else {
            makeText("Não conseguimos criar seu pedido")
        }
    }

    private fun sendBroadcastNewPedido() {
        val it = Intent()
        it.action = ActivityPedidoFinalizar.ACTION_PEDIDO_FINALIZED
        LocalBroadcastManager.getInstance( activity ).sendBroadcast( it )
    }

    fun getPedido() : Pedido {
        if( pedid == null )
            pedid = Pedido()

        return pedid!!
    }

    private fun mountPedido() {
        layoutTooObject( getPedido() )
        getPedido().addressEntrega = getAddressEntrega()
        getPedido().addressRetirada = getAddressRetirada()
        getPedido().idUser = getUsuario().id
        getPedido().local = getAddressRetirada().local
    }

    fun getUsuario() : Usuario {
        return LayoutMainActivity.getUsuario( activity )
    }

    override fun setPrecoMedio(preco : Int ) {
        getPedido().valor = UtilsConvert.toPreco( preco , getAddressEntrega() , getAddressRetirada() )
        txtTotal.text = getPedido().getLayoutPreco( resources )
    }
    abstract fun getAddressEntrega() : Address
    abstract fun getAddressRetirada(): Address
}


abstract class LayoutPedidoFinalizarBasic(methods: Interfaces.ActivityGetter) : LayoutBasic(methods), View.OnClickListener {
    private lateinit var editText : EditText
    private lateinit var edtHora  : EditText

    protected lateinit var txtTotal : TextView
    private lateinit var txtButton : View

    override fun recuperarReferencias(view: View) {
        editText = view.findViewById(R.id.edittext)
        edtHora = view.findViewById(R.id.edittext2)
        txtTotal = view.findViewById(R.id.txt_total)
        txtButton = view.findViewById(R.id.txt_button)
    }

    override fun setOnClick() {
        txtButton.setOnClickListener( this )
    }

    override fun bindViewHolder() { }

    override fun onClick(v: View?) {
        whenClickConfirmar()
    }

    abstract fun whenClickConfirmar()

    protected fun layoutTooObject(pedido : Pedido) {
        pedido.descPedido = editText.text.toString()
        pedido.horaPrazo = UtilsConvert.dateToDataMysql(edtHora.text.toString())
    }

    protected fun isValido(): Boolean {
        if(editText.text.toString().length < 15) {
            makeText("Faça uma descrição mais detalhada")
            return false
        }

        if( !UtilsValidate.validateHora( edtHora.text.toString()  ) ) {
            makeText("A data precisa estar no padrão dd/mm/aaaa")
            return false
        }

        return true
    }

    abstract fun setPrecoMedio(preco : Int)

}