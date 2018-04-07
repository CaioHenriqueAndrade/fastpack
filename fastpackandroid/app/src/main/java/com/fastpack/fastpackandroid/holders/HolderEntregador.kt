package com.fastpack.fastpackandroid.holders

import android.view.View
import com.fastpack.fastpackandroid.R

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.objetos.Usuario
import com.fastpack.fastpackandroid.objetos.UsuarioPrestador

/**
 * Created by Caio on 07/04/2018.
 */

class HolderEntregador(view: View, methods: Interfaces.AdapterRecyclerMethods) : Holder3txtCircleImageView(view, methods) {

    override fun iniciarDadosNoLayout() {
        imageView.setImageResource( R.drawable.profile )
        txtTitulo.text = getUsuario().nome
        txtSubtitle.text = "Entrega em sua região"
    }

    fun getUsuario() : Usuario {
        return `object` as UsuarioPrestador
    }
    override fun onClick(v: View) {

    }
}
