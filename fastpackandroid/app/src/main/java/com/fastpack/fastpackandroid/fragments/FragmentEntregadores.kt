package com.fastpack.fastpackandroid.fragments


import android.content.Intent
import android.os.Bundle
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityPedidoAceitar
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout_fragment.LayoutFragmentEntregadores
import com.fastpack.fastpackandroid.objetos.Pedido
import com.google.gson.Gson

/**
 * Created by Caio on 07/04/2018.
 */

class FragmentEntregadores : FragmentBasic() {


    companion object {
        fun newInstance(): FragmentEntregadores {
            val frag = FragmentEntregadores()
            frag.arguments = Bundle()
            return frag
        }
    }

    override fun getIdLayout(): Int {
        return R.layout.layout_fragment_entregadores
    }

    override fun getNewInstanceLayout(): Interfaces.LayoutFragmentBasicMethods {
        return LayoutFragmentEntregadores(this)
    }


}
