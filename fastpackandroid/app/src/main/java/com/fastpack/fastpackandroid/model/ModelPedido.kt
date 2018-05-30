package com.fastpack.fastpackandroid.model

import android.support.annotation.WorkerThread
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Pedido
import com.fastpack.fastpackandroid.objetos.Usuario

class ModelPedido(utils: Interfaces.ModelUtils) : ModelBasic(utils) {

    companion object {
        const val PARAM_INSERIR = 45
        const val PARAM_BUSCAR = 46
        const val PARAM_ACEITAR = 47
        const val PARAM_CANCELAR = 48
        const val PARAM_PROXIMOS = 49
        const val PARAM_FINALIZAR = 50
        const val PARAM_ATUALIZAR_ALL_PEDIDO = 51
    }

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        model.onDadosReceives(param, `object`, responseCode)
    }

    fun insertPedido(pedido: Pedido) {
        Thread({
            getRequisicao().requerer(PARAM_INSERIR, "POST", pedido, null, false, "pedido")
        }).start()
    }

    fun searchPedidos(usuario: Usuario) {
        Thread({
            var path = "pedido"
            if (usuario.isPrestador()) {
                path += "/prestador"
            }
            getRequisicao().getList(PARAM_BUSCAR, Pedido::class.java, path, usuario.id.toString(), "0")
        }).start()
    }

    fun aceitarPedido(usuario: Usuario, pedido: Pedido) {
        changeStatus(PARAM_ACEITAR,usuario , pedido , Pedido.STATUS_AGUARDE_ENTREGA )
    }

    private fun changeStatus(PARAM : Int , usuario: Usuario, pedido: Pedido, newStatus : Int) {

        val pedidoStatus = PedidoStatus.getNewInstance( usuario , pedido )

        pedidoStatus.newStatus = newStatus

        Thread({
            getRequisicao().requerer(  PARAM , "PUT", pedidoStatus , null , false,"pedido")
        }).start()

    }

    fun cancelarPedido(usuario: Usuario, pedido: Pedido) {
        changeStatus( PARAM_CANCELAR , usuario , pedido , Pedido.STATUS_CANCELADO )
    }

    fun searchPedidosParaPrestador(usuario: Usuario) {
        Thread({
            getRequisicao().getList( PARAM_PROXIMOS , Pedido::class.java ,
                    "pedido","proximos",usuario.local.latitude.toString(), usuario.local.longitude.toString() )
        }).start()
    }

    fun finalizarPedido(usuario: Usuario, pedido: Pedido) {
        changeStatus( PARAM_FINALIZAR , usuario , pedido , Pedido.STATUS_ENTREGUE )
    }

    fun atualizar(usuario : Usuario , idPedido : Int ) {
        Thread({
            getRequisicao().get( PARAM_ATUALIZAR_ALL_PEDIDO , Pedido() , "pedido/atualizar" , usuario.id.toString() , idPedido.toString() )
        }).start()
    }

}

class PedidoStatus {
     var idUser = 0
     var idPrestador = 0
     var newStatus = -5
     var idPedido = 0


    companion object {
        fun getNewInstance(usuario : Usuario , pedido : Pedido) : PedidoStatus {
            val p = PedidoStatus()
            p.idPedido = pedido.id
            p.idUser = usuario.id
            if( pedido.idPrestador == 0 ) {
                if( pedido.idUser != usuario.id ) {
                    p.idPrestador = usuario.id
                }
            }
            return p
        }
    }

}