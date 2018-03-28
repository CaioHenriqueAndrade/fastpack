package com.fastpack.fastpackandroid.layout;

import android.view.View;

import com.fastpack.fastpackandroid.interfaces.Interfaces;

/**
 * Created by root on 28/03/18.
 */

public abstract class LayoutBasic implements Interfaces.LayoutBasicMethods {


       @Override
    public void setOnClick() {
    }


    public void init(View view) {
        recuperarReferencias(view);
        iniciarDadosNoLayout();
        setOnClick();

    }
}
