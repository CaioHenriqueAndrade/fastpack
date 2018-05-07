package com.fastpack.fastpackandroid.activity

import android.os.Bundle
import com.fastpack.fastpackandroid.interfaces.Interfaces

class ActivityPedido : ActivityBasic() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBar()
    }

    override fun getLayoutBasic(): Interfaces.LayoutMethodsRequierieds {
        return
    }

    override fun getIdContainerView(): Int {

    }

    override fun getIdLayout(): Int {

    }
}
