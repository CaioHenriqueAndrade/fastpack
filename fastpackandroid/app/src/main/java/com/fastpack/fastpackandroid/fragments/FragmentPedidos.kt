package com.fastpack.fastpackandroid.fragments

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout_fragment.LayoutFragmentPedidos

class FragmentPedidos : FragmentBasic() {


    override fun getIdLayout(): Int {
        return R.layout.layout_fragment_pedidos
    }

    override fun getNewInstanceLayout(): Interfaces.LayoutFragmentBasicMethods {
        return LayoutFragmentPedidos( this )
    }

    companion object {

        fun newInstance(): FragmentPedidos {
            return FragmentPedidos()
        }
    }
}