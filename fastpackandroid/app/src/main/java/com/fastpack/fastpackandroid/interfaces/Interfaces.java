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
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.support.design.widget.TabLayout;

import com.fastpack.fastpackandroid.base_dados.MyCursor;
import com.fastpack.fastpackandroid.layout.LayoutBasic;
import com.fastpack.fastpackandroid.objetos.Address;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

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
        LayoutMethodsRequierieds getNewInstanceOfLayoutBasic();
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
        LayoutFragmentBasicMethods getNewInstanceLayout();
    }

    //LayoutFragmentBasicMethods extends essa interfae, olha os metodos la antes de alterar
    interface LayoutMethodsRequierieds extends LayoutBasicMethods {

        void onResume();
        void onStop();

    }

     interface LayoutBasicMethods extends StringUtils , ActivityGetter {
        void recuperarReferencias(View view);
        void bindViewHolder();
        void setOnClick();

        void init(View view);
    }
    interface LayoutFragmentBasicMethods extends LayoutBasicMethods, ActivityGetter {
        void makeText(String text);
        void onDestroy();
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




   interface ObjectBasicMethods<T> {

        T getDadosReceivs(MyCursor cursor);

        String getNameTable();

        String getWhereIdentificador();

    }


    interface OnMethodsDialog {
        Activity getActivity();
        LayoutInflater getLayoutInflater();
        AlertDialog onCreateDialog(AlertDialog alertDialogBuilder);
        boolean onBackPressed();
        String getString(int idRes);
        Resources getResources();
        void onPostViewCreated(View v);
        View onCreateView();
        AlertDialog getDialog();
        void recuperarReferencias( View view );
        void setOnClickListeners();
        void setDadosInLayout();
        void dismiss();
        boolean onTouchOutsideDialog();

        void setBackgroundTransparent();
    }

    interface MethodsOnDialogMap {
        void onPositiveButton(Address addressSelected);
        void onCloseDialog();
    }

    interface CallBackMapa {
        void onMapaCompleted(GoogleMap googleMap , MapView mapView);
    }

}
