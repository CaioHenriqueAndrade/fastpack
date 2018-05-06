package com.fastpack.fastpackandroid.utils;

import android.content.Context;

import dmax.dialog.SpotsDialog;

public class UtilsProgressDialog {
    //    compile 'com.github.d-max:spots-dialog:0.7@aar'
    SpotsDialog dialog;
    private Context context;

    public UtilsProgressDialog(Context context, String message) {
        this.context  = context;
        init(message);
    }

    public UtilsProgressDialog(Context context) {
        this.context  = context;
    }

    public void init(String msg) {
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        dialog = new SpotsDialog( context , msg );
    }
    public void show() {
        dialog.show();
    }

    public void setIndeterminate() {
        dialog.setCancelable( false );
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShow() {
        return dialog.isShowing();
    }

    public SpotsDialog getDialog() {
        return dialog;
    }
}
