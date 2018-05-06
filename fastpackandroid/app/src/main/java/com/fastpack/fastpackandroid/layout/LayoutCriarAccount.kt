package com.fastpack.fastpackandroid.layout

import android.content.Intent
import android.support.annotation.WorkerThread
import android.view.View
import android.widget.EditText
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityAddress
import com.fastpack.fastpackandroid.activity.MainActivity
import com.fastpack.fastpackandroid.base_dados.BancoManager
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.model.ModelUsuario
import com.fastpack.fastpackandroid.objetos.Address
import com.fastpack.fastpackandroid.objetos.Local
import com.fastpack.fastpackandroid.objetos.Usuario
import com.fastpack.fastpackandroid.utils.UtilsAction
import com.fastpack.fastpackandroid.utils.UtilsProgressDialog

class LayoutCriarAccount(methods: Interfaces.ActivityGetter, val usuario: Usuario) : LayoutCriarAccountLocal(methods), Interfaces.ModelUtils {

    private val utilsProgressDialog = UtilsProgressDialog( activity )

    override fun whenClicked() {
        if (isValido()) {
            showProgress()
            ModelUsuario(this).tentarInserir(usuario)
        }
    }

    fun isValido(): Boolean {
        if( address == null ){
            makeText("Precisamos do seu endereço para carregar entregadores em sua região" )
            return false
        }
        if(!getSenha().equals(usuario.password)) {
            makeText("Senhas incopatíveis!")
            limparCampo()
            showKeyboard()
            return false
        }

        mountUsuario()

        return true
    }

    private fun mountUsuario() {
        usuario.local = address!!.local
    }

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        if (param == ModelUsuario.PARAM_INSERIR) {
            if (responseCode == 200 && `object` != null) {
                val usuario = `object` as Usuario
                usuario.inserir(BancoManager(activity))
                activity.runOnUiThread({ whenSuccess() })
            } else if (responseCode == Usuario.RESPONSE_LOGIN_ERROR) {

                activity.runOnUiThread({
                    whenAlreadyExists()
                })
            } else {
                activity.runOnUiThread({ whenNotConected() })
            }

        } else throw IllegalStateException("not implemented $param ")
    }

    private fun whenNotConected() {
        dismissDialog()
        makeText("Não houve conexão")
    }

    private fun whenAlreadyExists() {
        dismissDialog()
        makeText("Já existe um usuário com estes dados!")
    }

    private fun whenSuccess() {
        dismissDialog()
        openMainActivity()
        activity.finish()
    }

    private fun openMainActivity() {
        val it = Intent(activity, MainActivity::class.java)
        activity.startActivity(it)
    }

    private fun dismissDialog() {
        utilsProgressDialog.dismiss()
    }

    private fun showProgress() {
        utilsProgressDialog.init("Enviando ao servidor...")
        utilsProgressDialog.setIndeterminate()
        utilsProgressDialog.show()
    }
}

abstract class LayoutCriarAccountLocal(methods: Interfaces.ActivityGetter) : LayoutCriarAccountBasic(methods) {

    var address : Address? = null

    override fun openBuscarLocal() {
        val it = Intent( activity , ActivityAddress::class.java )
        it.putExtra( ActivityAddress.KEY_ACAO , ActivityAddress.ACTION_LOGIN )
        activity.startActivity( it )
    }

    fun whenReceivAddress(ad: Address) {
        this.address = ad
    }

}


abstract class LayoutCriarAccountBasic(methods: Interfaces.ActivityGetter) : LayoutBasic(methods), View.OnClickListener {
    private lateinit var editText: EditText

    private lateinit var button: View
    private lateinit var buttonBuscarLocal : View

    override fun recuperarReferencias(view: View) {
        button = view.findViewById(R.id.txt_button)
        editText = view.findViewById(R.id.edt_senha)
        buttonBuscarLocal = view.findViewById(R.id.facybutton_buscarlocal)
    }

    override fun bindViewHolder() {}

    override fun setOnClick() {
        button.setOnClickListener(this)
        buttonBuscarLocal.setOnClickListener( this )
    }

    override fun onClick(v: View?) {
        if( v!!.id == button.id )
            whenClicked()
        else if(buttonBuscarLocal.id == v.id ) {
            openBuscarLocal()
        } else throw IllegalStateException("not implemented ${v.javaClass.name}")
    }

    abstract fun openBuscarLocal()
    abstract fun whenClicked()

    protected fun getSenha(): String {
        return editText.text.toString()
    }

    protected fun showKeyboard() {
        UtilsAction.showKeyboard( editText , activity )
    }
    protected fun limparCampo() {
        editText.text = null
    }
}