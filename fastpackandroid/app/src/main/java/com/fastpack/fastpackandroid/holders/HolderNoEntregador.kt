package com.fastpack.fastpackandroid.holders

import android.view.View
import com.fastpack.fastpackandroid.R

import com.fastpack.fastpackandroid.interfaces.Interfaces

class HolderNoEntregador(view: View, methods: Interfaces.AdapterRecyclerMethods) : HolderImageMsg(view, methods) {

    override fun bindViewHolder() {
        imageView.setImageResource(R.drawable.ic_circle_alert)
        textView.text = getString(R.string.aindanaoexistementre)
    }
}
