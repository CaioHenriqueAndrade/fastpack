package com.fastpack.fastpackandroid.interfaces;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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


    interface ActivityBasicMethods extends  ActivityMethdsVisible,LayoutMethods , ActivityGetter {
        LayoutBasicMethods getLayoutBasic();
        @IdRes int getIdContainerView();
    }

    interface LayoutMethods {
        @LayoutRes int getIdLayout();
    }

    interface ActivityMethdsVisible {
        boolean isAlreadyDestroyed();
        boolean isVisible();
    }
    interface StringUtils {
        String getString(@StringRes int idRes);
        Resources getResources();
    }

    interface ActivityGetter {
        Activity getActivity();
    }
    interface LayoutFragmentBasic extends ActivityGetter , LayoutMethods {
        LayoutFragmentBasicMethods getLayout();
    }

    //LayoutFragmentBasicMethods extends essa interfae, olha os metodos la antes de alterar
    interface LayoutBasicMethods extends StringUtils , ActivityGetter {
        void recuperarReferencias(View view);
        void iniciarDadosNoLayout();
        void setOnClick();

        void init(View view);
    }
    interface LayoutFragmentBasicMethods extends LayoutBasicMethods, ActivityGetter {
        void makeText(String text);
    }

    interface AdapterRecyclerMethods extends ActivityGetter {
        Object getObjectByPosition(int position);
        LayoutInflater getLayoutInflater();
    }

    interface Holder extends LayoutBasicMethods {
        Object getObject();
    }

    interface ModelUtils extends ModelBasic {

    }
}
