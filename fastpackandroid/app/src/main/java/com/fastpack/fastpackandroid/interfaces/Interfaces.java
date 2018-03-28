package com.fastpack.fastpackandroid.interfaces;


import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.fastpack.fastpackandroid.layout.LayoutBasic;

public interface Interfaces {

    interface ModelBasic {
        void onDadosReceives(int param , @Nullable Object object , int responseCode);
    }


    interface ActivityBasicMethods {
        boolean isAlreadyDestroyed();
        boolean isVisible();
        LayoutBasicMethods getLayoutBasic();
        @IdRes int getIdContainerView();
    }

    interface LayoutBasicMethods {
        void recuperarReferencias(View view);
        void iniciarDadosNoLayout();
        void setOnClick();

        void init(View view);

    }

}
