package com.fastpack.fastpackandroid.model

import android.support.annotation.WorkerThread
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Usuario

/**
 * Created by Caio on 07/04/2018.
 */

open class ModelUsuario(utils: Interfaces.ModelUtils) : ModelBasic(utils) {

    var usuario: Usuario? = null

    companion object {
        const val PARAM_TENTAR_LOGIN = 21
        const val PARAM_INSERIR = 22
    }


    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        when( param ) {
            PARAM_TENTAR_LOGIN  , PARAM_INSERIR -> {
                model.onDadosReceives( param , `object` , responseCode )
            }

            else -> { throw IllegalArgumentException("not implemented $param") }
        }
    }

    fun tentarLogin(cpf : String , senha : String ) {
        val action = Runnable {
            getRequisicao().get( PARAM_TENTAR_LOGIN , Usuario() , "usuario" , cpf , senha )
        }

        Thread( action ).start()
    }

    fun tentarInserir(usuario: Usuario) {
        val action = Runnable {
            getRequisicao().requerer( PARAM_INSERIR , "POST",usuario , Usuario()  , true , "usuario" )
        }

        Thread( action ).start()
    }

}
