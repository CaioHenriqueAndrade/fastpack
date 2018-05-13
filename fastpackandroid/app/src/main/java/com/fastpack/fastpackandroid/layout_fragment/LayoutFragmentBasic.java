package com.fastpack.fastpackandroid.layout_fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Toast;

import com.fastpack.fastpackandroid.interfaces.Interfaces;

/**
 * Created by Caio on 07/04/2018.
 */

public abstract class LayoutFragmentBasic implements Interfaces.LayoutFragmentBasicMethods {

    private Interfaces.LayoutFragmentBasic methods;

    public LayoutFragmentBasic(Interfaces.LayoutFragmentBasic methods) {
        this.methods = methods;
    }


    @Override
    public void init(View view) {
        recuperarReferencias( view );
        bindViewHolder();
        setOnClick();
    }

    public void startActivity(Intent it) {
        getActivity().startActivity( it );
    }

    @Override
    public String getString(@IdRes int idRes) {
        return getResources().getString( idRes );
    }

    @Override
    public Resources getResources() {
        return getActivity().getResources();
    }

    @Override
    public void setOnClick() {

    }

    @Override
    public void makeText(String text) {
        Toast.makeText( getActivity() , text , Toast.LENGTH_LONG ).show();
    }

    @Override
    public Activity getActivity() {
        return methods.getActivity();
    }
}
