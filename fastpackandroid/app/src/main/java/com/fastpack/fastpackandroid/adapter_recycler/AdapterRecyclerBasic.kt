package com.fastpack.fastpackandroid.adapter_recycler

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.holders.HolderLoading

import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by Caio on 07/04/2018.
 */

abstract class AdapterRecyclerBasic<T>(private val ac: Interfaces.ActivityGetter) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Interfaces.AdapterRecyclerMethods {

    var list: MutableList<T>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    companion object {
        const val TYPE_LOADING = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if( viewType == TYPE_LOADING ) {

            return HolderLoading( inflate( R.layout.layout_loading , parent ) ,this  )

        } else throw IllegalArgumentException("not implemented this view type $viewType")
    }

    protected fun inflate( idLayout : Int , parent: ViewGroup? ): View {
        return layoutInflater.inflate( idLayout , parent , false )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Interfaces.Holder).bindViewHolder()
    }

    override fun getActivity(): Activity {
        return ac.activity
    }

    override fun getObjectByPosition(position: Int): Any {
        return list!![position] as Any
    }

    override fun getLayoutInflater(): LayoutInflater {
        return ac.activity.layoutInflater
    }

    fun addListItem(obj: T) {
        list!!.add(obj)
        notifyItemInserted(list!!.size)
    }

    fun removeListItem(obj: T) {
        val position = list!!.indexOf(obj)

        if (position == -1) return

        list!!.removeAt(position)
        notifyItemRemoved(position)
    }
}
