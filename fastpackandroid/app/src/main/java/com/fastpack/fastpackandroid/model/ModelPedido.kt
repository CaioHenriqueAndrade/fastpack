package com.fastpack.fastpackandroid.model

import android.support.annotation.WorkerThread
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Pedido
import com.fastpack.fastpackandroid.objetos.Usuario

class ModelPedido(utils: Interfaces.ModelUtils) : ModelBasic(utils) {

    companion object {
        const val PARAM_INSERIR = 45
        const val PARAM_BUSCAR = 46
        const val PARAM_ATUALIZAR = 47
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

    fun searchAtualizacao(pedido: Pedido, idQuemRequisita : Int ) {


        getRequisicao().get( PARAM_ATUALIZAR ,   Pedido::class.java ,
                "pedido" , idQuemRequisita.toString() , pedido.id.toString())

    }

}
