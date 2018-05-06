package com.fastpack.fastpackandroid.dialog

import android.app.Activity
import android.support.annotation.WorkerThread
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ImageView
import com.fastpack.fastpackandroid.R
import com.fastpack.fastpackandroid.interfaces.VivaCepListener
import com.fastpack.fastpackandroid.objetos.Address
import com.fastpack.fastpackandroid.utils.UtilsProgressDialog
import com.fastpack.fastpackandroid.view.MyMaterialEditText
import com.fastpack.fastpackandroid.vivaocep.VivaCepApi

class OnDialogAddressZipCod(ac: Activity, private val listener : VivaCepListener) : OnDialogBasic(ac), View.OnClickListener, MyMaterialEditText.OnClickOkListener, VivaCepListener {


    private lateinit var imageViw: ImageView
    private lateinit var myMaterialEditText: MyMaterialEditText

    private var text: String? = null
        get() {
            return myMaterialEditText.text.toString()
        }

    private var utilsProgressDialog: UtilsProgressDialog? = null

    override fun onCreateView(): View? {
        return layoutInflater.inflate(R.layout.layout_dialog_text_sample, null)
    }

    override fun onCreateDialog(alertDialogBuilder: AlertDialog?): AlertDialog {
        val dialog = super.onCreateDialog(alertDialogBuilder)
        setBackgroundTransparent()
        return dialog
    }

    override fun recuperarReferencias(view: View?) {
        myMaterialEditText = view!!.findViewById(R.id.edt_descricao)
        imageViw = view!!.findViewById(R.id.imageview)
    }

    override fun setDadosInLayout() {
        myMaterialEditText.notifyCampoText()
        myMaterialEditText.setOnClickOkListener(this)
    }

    override fun setOnClickListeners() {
        imageViw.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        whenClicked()
    }


    //whenClicked IN ok in edittext or click in icon
    override fun whenClicked(): Boolean {
        if (text!!.length < 8) {
            makeText("Digite o CEP completo")
            return true
        }

        searchByZipCod()
        return true
    }

    private fun searchByZipCod() {
        showProgressDialog()
        VivaCepApi(this, text!!).start()
    }

    fun showProgressDialog() {
        getProgressDialog().init("Pesquisando...")
        getProgressDialog().show()
    }

    fun dismissProgressDialog() {
        getProgressDialog().dismiss()
    }

    fun getProgressDialog(): UtilsProgressDialog {
        if (utilsProgressDialog == null)
            utilsProgressDialog = UtilsProgressDialog(activity)

        return utilsProgressDialog!!
    }


    //quando a procura pelo cep e finalizada
    @WorkerThread
    override fun whenSearchFinished(address: Address?) {
        activity.runOnUiThread({
            dismissProgressDialog()
            dismiss()
            listener.whenSearchFinished( address )
        })
    }
}

