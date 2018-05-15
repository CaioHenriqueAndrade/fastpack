package com.fastpack.fastpackandroid.layout_fragment

import android.content.Intent
import android.support.annotation.WorkerThread
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityPedidoCriar
import com.fastpack.fastpackandroid.adapters.AdapterRecyclerPedido

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutMainActivity
import com.fastpack.fastpackandroid.model.ModelPedido
import com.fastpack.fastpackandroid.objetos.Pedido
import com.fastpack.fastpackandroid.objetos.Usuario
import com.fastpack.fastpackandroid.utils.UtilsLayoutManager

class LayoutFragmentPedidos(methods: Interfaces.LayoutFragmentBasic) : LayoutFragmentPedidosModel(methods) {

    fun whenActionPedidoAtualized( pedido : Pedido ) {
        adapterPedidos.notifyItemChanged( pedido )
    }
}

abstract class LayoutFragmentPedidosModel( methods : Interfaces.LayoutFragmentBasic ) : LayoutFragmentPedidosBasic(methods), Interfaces.ModelUtils {

    companion object {
        var list : MutableList<Pedido>? = null
    }

    private val modelPedido = ModelPedido( this )

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {

        if( param == ModelPedido.PARAM_BUSCAR ) {

            if( responseCode == 200 && `object`  != null) {
                list = `object` as MutableList<Pedido>?
            }

            activity.runOnUiThread({ whenFinishRequisicaoBuscar() })
        }
    }

    private fun whenFinishRequisicaoBuscar() {
        setList()
    }

    override fun searchListIfNeeded() {
        if( list == null ) {
            modelPedido.searchPedidos( getUsuario() )
        } else
            setList()
    }

    private fun setList() {
        adapterPedidos.list = list
    }

    private fun getUsuario() : Usuario {
        return LayoutMainActivity.getUsuario( activity )
    }

    override fun whenClickedInFabButton() {
        if( getUsuario().isPrestador ) {

        } else {
            if( isPossivelCriarNewPedido() ) {
                val it = Intent( activity , ActivityPedidoCriar::class.java )
                it.putExtra( ActivityPedidoCriar.KEY_MEDIA , getMedia() )
                startActivity( it )
            }
        }
    }

    fun isPossivelCriarNewPedido() : Boolean {
        val existsUsersPrestadores = LayoutFragmentEntregadores.listUsuariosPrestador != null &&
                LayoutFragmentEntregadores.listUsuariosPrestador!!.size > 0

        if( !existsUsersPrestadores ) {
            makeText("Não existem entregadores em seu região!")
            return false
        }

        return true
    }

    fun getMedia() : String {
        var total = 0

        var totalDeUsers = LayoutFragmentEntregadores.listUsuariosPrestador!!.size

        for (usuarioPrestador in LayoutFragmentEntregadores.listUsuariosPrestador!!) {
            total += usuarioPrestador.precoMedio
        }

        return ( total / totalDeUsers ).toString()
    }
}


abstract class LayoutFragmentPedidosBasic(methods: Interfaces.LayoutFragmentBasic) : LayoutFragmentBasic (methods), View.OnClickListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var fab : FloatingActionButton

    protected val adapterPedidos = AdapterRecyclerPedido( this )

    override fun recuperarReferencias(view: View) {
        recyclerView = view.findViewById(R.id.recyclerview)
        fab = view.findViewById(R.id.fab)
    }

    override fun bindViewHolder() { initRecycler(); searchListIfNeeded()  }

    override fun setOnClick() {
        fab.setOnClickListener( this )
    }
    private fun initRecycler() {
        recyclerView.layoutManager = UtilsLayoutManager.getLayoutManager( activity )
        recyclerView.adapter = adapterPedidos
    }

    override fun onClick(v: View?) {
        if(v!!.id == fab.id) {
           whenClickedInFabButton()
        } else throw IllegalStateException("not implemented ${ v!!.javaClass.name }")
    }

    abstract fun whenClickedInFabButton()

    abstract fun searchListIfNeeded()
}
