package com.fastpack.fastpackandroid.model

import android.support.annotation.WorkerThread
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Usuario
import com.fastpack.fastpackandroid.objetos.UsuarioPrestador

/**
 * Created by Caio on 07/04/2018.
 */

class ModelUsuarioPrestador(model : Interfaces.ModelUtils) : ModelUsuario( model ) {

    val usuarioPrestador: UsuarioPrestador
        get() = usuario as UsuarioPrestador


    fun buscar(usuario: Usuario) {
        if(usuario.getId() == 0)
            throw IllegalStateException("not search in user == 0");

        val action = Runnable { getRequisicao().getList(PARAM_PRESTADORES, UsuarioPrestador::class.java,
                "prestador", usuario.local.latitude.toString() , usuario.local.longitude.toString() ) }
        Thread( action ).start()
    }

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        when ( param ) {
            PARAM_PRESTADORES -> {
                model.onDadosReceives( param , `object` , responseCode )
            }
        }
    }

    companion object {
         val PARAM_PRESTADORES = 5
    }
}
