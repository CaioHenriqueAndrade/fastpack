package com.fastpack.fastpackandroid.activity

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces

class ActivityPedidoAceitar : ActivityBasic() {

    override fun getLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return
    }

    override fun getIdContainerView(): Int {
        return R.id.container
    }

    override fun getIdLayout(): Int {
        return R.layout.activity_pedido_aceitar
    }
}
