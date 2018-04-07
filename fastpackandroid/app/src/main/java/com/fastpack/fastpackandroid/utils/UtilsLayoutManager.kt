package com.fastpack.fastpackandroid.utils

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by Caio on 07/04/2018.
 */

object UtilsLayoutManager {

    fun getLayoutManager(context: Context): RecyclerView.LayoutManager {
        //se estamos em um tablet
        return if (UtilsScreen.isTablet(context.resources)) {
            GridLayoutManager(context, 2)
        } else
            LinearLayoutManager(context)
    }
}
