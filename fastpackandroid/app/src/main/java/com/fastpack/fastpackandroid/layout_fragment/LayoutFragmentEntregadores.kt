package com.fastpack.fastpackandroid.layout_fragment

import android.support.annotation.WorkerThread
import android.support.v7.widget.RecyclerView
import android.view.View
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.adapter_recycler.AdapterRecyclerEntregadores

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutMainActivity
import com.fastpack.fastpackandroid.model.ModelUsuarioPrestador
import com.fastpack.fastpackandroid.objetos.Pedido
import com.fastpack.fastpackandroid.objetos.Usuario
import com.fastpack.fastpackandroid.objetos.UsuarioPrestador
import com.fastpack.fastpackandroid.utils.UtilsLayoutManager

/**
 * Created by Caio on 07/04/2018.
 */

class LayoutFragmentEntregadores(methods: Interfaces.LayoutFragmentBasic) : LayoutFragmentBasic(methods), Interfaces.ModelUtils {

    private lateinit var recyclerView: RecyclerView
    private val adapter = AdapterRecyclerEntregadores(this)
    private val modelPrestador = ModelUsuarioPrestador(this)

    companion object {
        var listUsuariosPrestador: MutableList<UsuarioPrestador>? = null
    }

    override fun recuperarReferencias(view: View) {
        recyclerView = view.findViewById(R.id.recyclerview)
    }

    override fun bindViewHolder() {

        //tenta pegar a lista do servidor se nao exists
        //se nao seta no adapter
        verifyList()

        initRecycler()
    }

    private fun verifyList() {
        if (listUsuariosPrestador == null)
            initSearch()
        else
            adapter.list = listUsuariosPrestador
    }

    private fun initSearch() {
        modelPrestador.buscar(getUsuario())
    }

    private fun getUsuario(): Usuario {
        return LayoutMainActivity.getUsuario( activity )
    }

    private fun initRecycler() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = UtilsLayoutManager.getLayoutManager(activity)
    }

    @WorkerThread
    override fun onDadosReceives(param: Int, `object`: Any?, responseCode: Int) {
        when (param) {
            ModelUsuarioPrestador.PARAM_PRESTADORES -> {
                activity?.runOnUiThread({ onPostRequisicao( responseCode , `object` ) })
            }
        }
    }

    fun onPostRequisicao(responseCode : Int , obj : Any? ) {
        if (responseCode == 200) {
            listUsuariosPrestador = obj as MutableList<UsuarioPrestador>
            adapter.list = listUsuariosPrestador
        } else {
            makeText("Não houve conexão com o servidor")
        }
    }

}
