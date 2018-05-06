package com.fastpack.fastpackandroid.layout

import android.content.Intent
import android.view.View
import android.widget.EditText
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityCriarAccount
import com.fastpack.fastpackandroid.activity.MainActivity
import com.fastpack.fastpackandroid.base_dados.BancoManager

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.model.ModelUsuario
import com.fastpack.fastpackandroid.objetos.Usuario
import com.fastpack.fastpackandroid.utils.UtilsProgressDialog


class LayoutLogin(methods: Interfaces.ActivityGetter) : LayoutLoginBasic(methods), Interfaces.ModelUtils {
    val utilsProgressDialog = UtilsProgressDialog( activity )

    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        when( param ) {
            ModelUsuario.PARAM_TENTAR_LOGIN -> {
                val action = if( responseCode == 200 && `object` != null ) {
                    (`object` as Usuario).inserir(BancoManager(activity))

                    Runnable {
                        dismissProgress()
                        openMainActivity()
                    }
                } else if(responseCode == Usuario.RESPONSE_LOGIN_ERROR ) {
                    Runnable {
                        dismissProgress()
                        makeText("Login incorreto!")
                    }
                } else{

                    Runnable {
                        dismissProgress()
                        makeText("Não houve conexão!")
                    }

                }

                activity.runOnUiThread( action )
            }
            else -> {
                throw IllegalStateException("not implemented $param ")
            }
        }
    }

    override fun criarAccount() {
        val it = Intent( activity , ActivityCriarAccount::class.java )
        it.putExtra( ActivityCriarAccount.EXTRA_USUARIO , getNewUsuario().toJson() )
        activity.startActivity( it )
    }

    private fun getNewUsuario(): Usuario {
        val usuario = Usuario()
        usuario.cpf = getCpf()
        usuario.password = getSenha()
        return usuario
    }

    private fun openMainActivity() {
        activity.startActivity( Intent( activity , MainActivity::class.java ) )
    }

    override fun tentarLogin() {
        showProgress()
        ModelUsuario( this ).tentarLogin( getCpf() , getSenha() )
    }

    fun showProgress() {
        utilsProgressDialog.init( getString(R.string.enviandoaoservidor) )
        utilsProgressDialog.setIndeterminate()
        utilsProgressDialog.show()
    }

    fun dismissProgress() {
        utilsProgressDialog.dismiss()
    }

    fun existsUser(): Boolean {
        val user = Usuario()
        user.buscar( BancoManager( activity ) , null )
        return user.id != 0
    }
}


abstract class LayoutLoginBasic(methods: Interfaces.ActivityGetter) : LayoutBasic(methods), View.OnClickListener {

    private lateinit var edtCpf: EditText
    private lateinit var edtSenha: EditText

    private lateinit var buttonEntrar : View
    private lateinit var buttonCriar  : View

    override fun recuperarReferencias(view: View) {
        edtCpf = view.findViewById(R.id.edt_cpf)
        edtSenha = view.findViewById(R.id.edt_senha)

        buttonCriar = view.findViewById(R.id.facybutton_criar)
        buttonEntrar = view.findViewById(R.id.facybutton_entrar)
    }

    override fun setOnClick() {
        buttonCriar.setOnClickListener( this )
        buttonEntrar.setOnClickListener( this )
    }

    override fun bindViewHolder() {

    }

    override fun onClick(v: View?) {
        val msg = validate()
        if( msg != null ) {
            makeText( msg )
            return
        }

        if( v!!.id == buttonCriar.id ) {
            criarAccount()
        } else if( v.id == buttonEntrar.id ) {
            tentarLogin()
        } else throw IllegalStateException("not implemented this action")
    }

    abstract fun tentarLogin()
    abstract fun criarAccount()

    private fun validate(): String? {
        val cpf = getCpf()
        if( cpf.length != 11 ) {
            return "Cpf inválido"
        }

        val senha = getSenha()
        if( senha.length < 5  ||
                senha.length > 45 ) {
            return "Senha invalida"
        }

        return null
    }

    protected fun getSenha() : String {
        return edtSenha.text.toString()
    }
    protected fun getCpf(): String {
        return edtCpf.text.toString()
    }
}
