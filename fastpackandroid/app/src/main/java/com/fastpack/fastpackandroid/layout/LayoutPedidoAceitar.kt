package com.fastpack.fastpackandroid.layout

import android.content.DialogInterface
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
import com.fastpack.fastpackandroid.utils.UtilsDialog
import com.fastpack.fastpackandroid.utils.UtilsProgressDialog
import com.google.gson.Gson

class LayoutPedidoAceitar(methods: Interfaces.ActivityGetter) : LayoutPedidoAceitarQuestion(methods) {
    override fun recuperarReferencias(view: View) {
        super.recuperarReferencias(view)
        searchAtualizacaoDeStatus() //pega este mesmo pedido porem atualizado
    }

    private fun searchAtualizacaoDeStatus() {
        modelPedido.atualizar( getUsuario() , pedido.id )
    }

    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        if( param == ModelPedido.PARAM_ATUALIZAR_ALL_PEDIDO ) {
            if( responseCode == 200 && `object` != null ) {
                pedido?.notifyAtualizacao( `object` as Pedido )
                activity?.runOnUiThread({ atualizarPedidoInLayoutAndSendBroad() })
            }
        } else
            super.onDadosReceives(param, `object`, responseCode)
    }
}

abstract class LayoutPedidoAceitarQuestion(methods: Interfaces.ActivityGetter) : LayoutPedidoAceitarModel(methods) {
    override fun openDialogQuestAction() {
        UtilsDialog(activity).dialogBasic("O que deseja fazer?", "Terminar Pedido", "Cancelar pedido", { dialog, which ->
            openDialogConfirmed(0)
        }) { dialog, which ->
            openDialogConfirmed(1)
        }
    }

    private fun openDialogConfirmed(action: Int) {
        val msg = if(action == 0) {
            "Tem certeza que deseja encerrar este pedido?"
        } else {
            "Tem certeza que deseja cancelar a entrega deste pedido?"
        }

        UtilsDialog( activity ).dialogBasic( msg , "Confirmar" , "Cancelar" ) { dialog,wich ->
            if( action == 0 ) {
                finalizarPedido()
            } else {
                cancelarPedido()
            }
        }

    }

}
abstract class LayoutPedidoAceitarModel(methods: Interfaces.ActivityGetter) : LayoutPedidoAceitarInterationUser(methods), Interfaces.ModelUtils {
    private val progressDialog = UtilsProgressDialog(activity)

    override fun changeStatus() {

        if (pedidoIsDoUser()) {

            //se e do usuario e o pedido e clicavel ainda,
            //ou seja, ainda e possivel modificar o status
            // entao com certeza o usuarioo esta cancelando este pedido
            cancelarPedido()
        } else {

            //se nao for o pedido do usuario, e o usuario do estabelecimento
            //aceitando este pedido
            aceitarPedido()
        }
    }

    protected fun aceitarPedido() {
        showProgressDialog()
        modelPedido.aceitarPedido(getUsuario(), pedido)
    }

    protected fun finalizarPedido() {
        showProgressDialog()
        modelPedido.finalizarPedido(getUsuario(), pedido)
    }

    protected fun cancelarPedido() {
        showProgressDialog()
        modelPedido.cancelarPedido(getUsuario(), pedido)
    }

    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        if (param == ModelPedido.PARAM_ACEITAR) {

            activity?.runOnUiThread({ whenReturnAccepted(responseCode) })

        } else if (param == ModelPedido.PARAM_CANCELAR) {

            activity?.runOnUiThread({ whenCanceled(responseCode) })

        } else if (param == ModelPedido.PARAM_FINALIZAR) {
            activity?.runOnUiThread({ whenPedidoFinalized(responseCode) })
        } else throw IllegalStateException("not implemented $param")
    }

    private fun whenPedidoFinalized(responseCode: Int) {
        dismissProgress()
        if (responseCode == 200) {
            makeText("Finalizado com sucesso!")
            pedido.status = Pedido.STATUS_ENTREGUE
            bindViewHolder()
        } else {
            makeText("Não foi possível finalizar")
        }
    }

    private fun whenCanceled(responseCode: Int) {
        dismissProgress()
        if (responseCode == 200) {
            makeText("Atualizado com sucesso")
            changeStatusInPedido(Pedido.STATUS_CANCELADO)
        } else {
            makeText("Não foi possível cancelar")
        }
    }

    fun changeStatusInPedido(newStatus: Int) {
        pedido.status = newStatus
        atualizarPedidoInLayoutAndSendBroad()
    }

    protected fun atualizarPedidoInLayoutAndSendBroad() {
        bindViewHolder()
        sendBroadcastPedidoChanged()
    }

    private fun whenReturnAccepted(responseCode: Int) {
        dismissProgress()
        if (responseCode == 200) {
            makeText("Alterado com sucesso!")
            pedido.idPrestador = getUsuario().id
            changeStatusInPedido(Pedido.STATUS_AGUARDE_ENTREGA)
        } else {
            makeText("Não foi possível modificar")
        }
    }

    fun showProgressDialog() {
        progressDialog.init("Enviando...")
        progressDialog.setIndeterminate()
        progressDialog.show()
    }

    fun dismissProgress() {
        progressDialog.dismiss()
    }
}
abstract class LayoutPedidoAceitarInterationUser(methods: Interfaces.ActivityGetter) : LayoutPedidoAceitarBasic(methods), DialogInterface.OnClickListener, Interfaces.ModelUtils {
    protected var modelPedido = ModelPedido(this )

    override fun bindViewHolder() {
        objectToLayout(pedido, getTypeOfObjectLayout())
        super.bindViewHolder()
    }

    fun getTypeOfObjectLayout(): Int {
        if ( isUsuarioAlterando() ) {
            return ALL_ADDRESS
        } else if ( isPrestadorAlterando() ) {
            return ALL_ADDRESS
        } else {
            return ADDRESS_SAMPLE
        }
    }

    fun sendBroadcastPedidoChanged() {
        val it = Intent()
        it.action = ActivityPedidoAceitar.ACTION_PEDIDO_CHANGED
        it.putExtra(ActivityPedidoAceitar.EXTRA_PEDIDO_CHANGED , pedido.toJson() )
        LocalBroadcastManager.getInstance( activity ).sendBroadcast( it )
    }

    override fun onClick(v: View?) {
        if (pedido.isJustEnviadoAoServer) {
            openDialogClick()
        } else if (pedido.isAguardandoEntrega) {
            if( isPrestadorAlterando() ) {
                openDialogQuestAction()
            }
        }
    }

    private fun openDialogClick() {

        val msg = if (pedido.idUser == getUsuario().id) {
            "Deseja excluir este pedido?"
        } else {
            "Aceitar esse pedido para realizar a entrega e o endereço ser liberado?"
        }

        UtilsDialog(activity).dialogBasic(msg, "Confirmar", "Cancelar", this)

    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        dialog.dismiss()
        changeStatus()

    }

    protected fun isUsuarioAlterando() : Boolean {
        return getUsuario().id == pedido.idUser
    }
    protected fun isPrestadorAlterando() : Boolean {
        return pedido.idPrestador != 0 && !isUsuarioAlterando()
    }

    abstract fun changeStatus()
    abstract fun openDialogQuestAction()

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

abstract class LayoutPedidoAceitarBasic(methods: Interfaces.ActivityGetter) : LayoutBasic(methods), View.OnClickListener {

    lateinit var pedido: Pedido

    companion object {
        const val ALL_ADDRESS = 0
        const val ADDRESS_SAMPLE = 1
    }

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

    override fun bindViewHolder() {
        txtButton.text = getTextButton()
    }

    override fun setOnClick() {
        txtButton.setOnClickListener( this )
    }

    protected fun pedidoIsDoUser() : Boolean {
        return pedido.idUser ==  getUsuario().id
    }

    private fun getTextButton(): CharSequence {
        if( pedido.isJustEnviadoAoServer ) {
            if( pedidoIsDoUser() ) {
                return "Cancelar Pedido"
            } else {
                return "Aceitar Pedido"
            }
        } else if( pedido.isCancelado ) {
            return "Pedido Cancelado"
        } else if(pedido.isEntregue) {
            return "Pedido entregue"
        } else if( pedido.isAguardandoEntrega) {
            return "Pedido aceito e aguardando entrega"
        }

        throw IllegalStateException("not implemented")
    }


    fun getUsuario() : Usuario {
        return LayoutMainActivity.getUsuario( activity )
    }

    protected fun objectToLayout(pedido : Pedido, type : Int ) {
        txtComentarios.text = pedido.descPedido
        txtPreco.text = pedido.getLayoutPreco( resources )
        if( type == ALL_ADDRESS ) {
            txtAddressEntrega.text = pedido.addressEntrega.formatAll()
            txtAddressRetirada.text = pedido.addressRetirada.formatAll()
        } else if(type == ADDRESS_SAMPLE) {
            txtAddressEntrega.text = pedido.addressEntrega.format()
            txtAddressRetirada.text = pedido.addressRetirada.format()
        } else throw IllegalStateException("IllegalStateException e")

        txtStatus.text = pedido.getStatusLayout( resources )
    }

    fun notifyStatusChanged(pedido : Pedido) {
        txtStatus.text = pedido.getStatusLayout( resources )
    }
}

