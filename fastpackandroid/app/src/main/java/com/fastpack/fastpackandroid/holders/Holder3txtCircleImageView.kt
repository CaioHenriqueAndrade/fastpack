package com.fastpack.fastpackandroid.holders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by Caio on 07/04/2018.
 */

//layout3txt_circleimageview
abstract class Holder3txtCircleImageView(view: View, methods: Interfaces.AdapterRecyclerMethods) : HolderBasic(view, methods), View.OnClickListener {


    lateinit var txtTitulo: TextView
    lateinit var txtSubtitle: TextView
    lateinit var txtOptional: TextView
    lateinit var imageView: ImageView
    lateinit var container: ViewGroup

    override fun recuperarReferencias(view: View) {
        imageView = view.findViewById<View>(R.id.imageview) as ImageView
        txtTitulo = view.findViewById<View>(R.id.txt_titulo) as TextView
        txtSubtitle = view.findViewById<View>(R.id.txt_1) as TextView
        txtOptional = view.findViewById<View>(R.id.txt_2) as TextView
        container = view.findViewById<View>(R.id.container) as ViewGroup
    }

    override fun setOnClick() {
        container.setOnClickListener( this )
    }

}
