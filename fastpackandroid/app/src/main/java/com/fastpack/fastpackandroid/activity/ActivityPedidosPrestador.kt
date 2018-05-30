package com.fastpack.fastpackandroid.activity

import android.os.Bundle
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.layout.LayoutPedidosPrestador

class ActivityPedidosPrestador : ActivityBasic() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar()
        setAllActionBar()
    }
    override fun getNewInstanceOfLayoutBasic(): Interfaces.LayoutMethodsRequierieds? {
        return LayoutPedidosPrestador( this )
    }

    override fun getIdContainerView(): Int {
        return R.id.container
    }

    override fun getIdLayout(): Int {
        return R.layout.layout_with_recyclerview
    }
}
