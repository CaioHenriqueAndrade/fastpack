package com.fastpack.fastpackandroid.layout

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Button
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.activity.ActivityAddress

import com.fastpack.fastpackandroid.interfaces.Interfaces
import com.fastpack.fastpackandroid.permission.Permicao

class LayoutActivityAddress(methods: Interfaces.ActivityGetter) : LayoutBasic(methods), View.OnClickListener {


    protected val layoutAddress = LayoutAddress(this)

    private lateinit var button: View

    var action: String = ""

    override fun recuperarReferencias(view: View) {
        layoutAddress.init(view)
        button = view.findViewById(R.id.button)
    }

    override fun bindViewHolder() {}

    override fun setOnClick() {
        button.setOnClickListener(this)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (Permicao.onRequestPermissionResult(requestCode, permissions, grantResults, requestCode)) {
            layoutAddress.onPermissionResultGranted()
        }
    }

    override fun onClick(v: View?) {
        if (layoutAddress.validate(layoutAddress.address)) {
            sendAddress()
            activity.finish()
        }
    }

    private fun sendAddress() {
        val it = Intent()
        it.action = action
        it.putExtra( ActivityAddress.KEY_ADDRESS , layoutAddress.address.toJson() )
        LocalBroadcastManager.getInstance( activity ).sendBroadcast( it )
    }

}
