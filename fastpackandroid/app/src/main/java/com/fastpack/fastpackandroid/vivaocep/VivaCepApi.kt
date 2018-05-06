package com.fastpack.fastpackandroid.vivaocep

import android.support.annotation.WorkerThread
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.interfaces.VivaCepListener
import com.fastpack.fastpackandroid.objetos.Address
import com.fastpack.fastpackandroid.requisicoes.Requisicoes

class VivaCepApi(private val listener: VivaCepListener, cep: String) : Interfaces.ModelBasic {

    val allPath = "https://viacep.com.br/ws/" + cep +"/json/"
    internal var action: Runnable = Runnable {

        requisicao.get( 0 , allPath , VivaCepObject() )
    }

    private val requisicao: Requisicoes
        get() = Requisicoes(this)

    fun start() {
        Thread(action).start()
    }

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        if( responseCode == 200 ) {
            listener.whenSearchFinished( ( `object` as VivaCepObject).tooAddress() )
        } else
            listener.whenSearchFinished( null )
    }
}


internal class VivaCepObject {
    var logradouro : String? = null
    var complemento : String? = null
    var bairro : String? = null
    var localidade : String? = null
    var uf : String? = null
    var cep : String? = null
    fun tooAddress() : Address {
        val ad = Address()
        ad.city          = localidade
        ad.neighborhood  = bairro
        ad.state         = uf
        ad.complementary = complemento
        ad.street        = logradouro
        ad.zipcode       = cep!!.replace("-","")
        ad.country       = "br"

        return ad
    }
}