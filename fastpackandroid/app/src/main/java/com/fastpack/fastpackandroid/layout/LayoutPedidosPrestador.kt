package com.fastpack.fastpackandroid.layout

import android.support.annotation.WorkerThread
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.adapters.AdapterRecyclerPedido

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.model.ModelPedido
import com.fastpack.fastpackandroid.objetos.Pedido
import com.fastpack.fastpackandroid.objetos.Usuario

class LayoutPedidosPrestador(methods: Interfaces.ActivityGetter) : LayoutPedidosPrestadorBasic(methods), Interfaces.ModelUtils {


    private var list : MutableList<Pedido>? = null
    private val modelPedido = ModelPedido( this )

    override fun initList() {
        if( list == null ) {
            searchList()
        } else {
            adapterPedidos.list = list
        }
    }

    private fun searchList() {
        modelPedido.searchPedidosParaPrestador( getUsuario() )
    }

    fun getUsuario() : Usuario {
        return LayoutMainActivity.getUsuario( activity )
    }

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        if(param == ModelPedido.PARAM_PROXIMOS) {
            if( responseCode == 200 && `object` != null  ) {
                list = `object` as MutableList<Pedido>
            }

            activity?.runOnUiThread({ whenSearchFinalized() })
        } else throw IllegalStateException("not implemented $param")
    }

    private fun whenSearchFinalized() {
        adapterPedidos.list = list
    }
}
abstract class LayoutPedidosPrestadorBasic(methods: Interfaces.ActivityGetter) : LayoutBasic(methods) {
    protected val adapterPedidos = AdapterRecyclerPedido( this )
    private lateinit var recyclerView : RecyclerView


    override fun recuperarReferencias(view: View) {
        recyclerView = view.findViewById(R.id.recyclerview)
    }

    override fun bindViewHolder() {
        initList()
        recyclerView.layoutManager = LinearLayoutManager( activity )
        recyclerView.adapter = adapterPedidos
    }

    abstract fun initList()

}
