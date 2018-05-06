package com.fastpack.fastpackandroid.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces

abstract class HolderImageMsg(view: View, methods: Interfaces.AdapterRecyclerMethods) : HolderBasic(view, methods) {
    protected lateinit var textView: TextView
    protected lateinit var imageView: ImageView

    override fun recuperarReferencias(view: View) {
        textView = view.findViewById(R.id.txt)
        imageView= view.findViewById(R.id.imageview)
    }
}
