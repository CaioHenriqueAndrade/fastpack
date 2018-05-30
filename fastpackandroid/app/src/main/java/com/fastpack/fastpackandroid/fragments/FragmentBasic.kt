package com.fastpack.fastpackandroid.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by Caio on 07/04/2018.
 */

abstract class FragmentBasic : Fragment(), Interfaces.LayoutFragmentBasic {

    lateinit var layout : Interfaces.LayoutFragmentBasicMethods
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(idLayout, container, false)
        layout = newInstanceLayout

        layout.init(view)
        return view
    }

    override fun onDestroy() {
        layout.onDestroy()
        super.onDestroy()
    }

}
