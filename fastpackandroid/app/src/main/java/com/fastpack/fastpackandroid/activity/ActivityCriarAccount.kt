package com.fastpack.fastpackandroid.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutCriarAccount
import com.fastpack.fastpackandroid.objetos.Address
import com.fastpack.fastpackandroid.objetos.Usuario
import com.google.gson.Gson

class ActivityCriarAccount : ActivityBasicService() {


    companion object {
        const val EXTRA_USUARIO = "EXTRA"
    }

    private lateinit var jsonUsuario : String

    override fun getLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return LayoutCriarAccount( this  , getUsuario() )
    }

    private fun getUsuario(): Usuario {
        jsonUsuario = getJson()
        return Gson().fromJson( jsonUsuario , Usuario::class.java )
    }

    private fun getJson(): String {
        return intent.getStringExtra( EXTRA_USUARIO )
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString( EXTRA_USUARIO , jsonUsuario )
        super.onSaveInstanceState(outState)
    }

    override fun getIdContainerView(): Int {
        return R.id.container
    }

    override fun getIdLayout(): Int {
        return R.layout.activity_criar_account
    }

    override fun onReceiv(intent: Intent) {
        if( intent.action.equals( ActivityAddress.ACTION_LOGIN ) ) {
            (layout as LayoutCriarAccount).whenReceivAddress( Gson().fromJson( intent.getStringExtra( ActivityAddress.KEY_ADDRESS ) , Address::class.java ) )
        } else throw IllegalStateException("not implemented ${intent.action}")
    }

    override fun getActions(): Array<String>? {
        return arrayOf( ActivityAddress.ACTION_LOGIN )
    }
}
