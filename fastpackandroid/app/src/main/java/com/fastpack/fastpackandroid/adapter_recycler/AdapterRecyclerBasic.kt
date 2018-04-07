package com.fastpack.fastpackandroid.adapter_recycler

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

import com.fastpack.fastpackandroid.interfaces.Interfaces

/**
 * Created by Caio on 07/04/2018.
 */

abstract class AdapterRecyclerBasic<T>(private val ac: Interfaces.ActivityGetter) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Interfaces.AdapterRecyclerMethods {

    var list: MutableList<T>? = null
    set(value) { notifyDataSetChanged() }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Interfaces.Holder).iniciarDadosNoLayout()
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
