package com.fastpack.fastpackandroid.interfaces;


import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TableLayout;
import android.support.design.widget.TabLayout;

import com.fastpack.fastpackandroid.layout.LayoutBasic;

public interface Interfaces {

    interface ModelBasic {
        void onDadosReceives(int param , @Nullable Object object , int responseCode);
    }

    interface AdapterTabbed extends StringUtils {
        FragmentManager getSupportV4FragmentManager();
        TabLayout getTabLayout();
        ViewPager getViewPager();
    }


    interface ActivityBasicMethods {
        boolean isAlreadyDestroyed();
        boolean isVisible();
        LayoutBasicMethods getLayoutBasic();
        @IdRes int getIdContainerView();
    }

    interface StringUtils {
        String getString(@IdRes int idRes);
        Resources getResources();
    }
    interface LayoutBasicMethods {
        void recuperarReferencias(View view);
        void iniciarDadosNoLayout();
        void setOnClick();

        void init(View view);

    }

}
