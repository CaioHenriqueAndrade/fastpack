package com.fastpack.fastpackandroid.holders

import android.view.View
import com.fastpack.fastpackandroid.R

import com.fastpack.fastpackandroid.interfaces.Interfaces

class HolderNoPedido(view: View, methods: Interfaces.AdapterRecyclerMethods) : HolderImageMsg(view, methods) {

    override fun bindViewHolder() {
        imageView.setImageResource(R.drawable.ic_circle_alert)
        textView.text = "Não conseguimos conexão :/"

    }
}
