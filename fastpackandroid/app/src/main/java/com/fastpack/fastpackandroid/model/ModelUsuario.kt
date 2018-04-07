package com.fastpack.fastpackandroid.model

import android.support.annotation.WorkerThread
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Usuario

/**
 * Created by Caio on 07/04/2018.
 */

open class ModelUsuario(utils: Interfaces.ModelUtils) : ModelBasic(utils) {

    var usuario: Usuario? = null

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {

    }
}
