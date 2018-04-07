package com.fastpack.fastpackandroid.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastpack.fastpackandroid.interfaces.Interfaces;

/**
 * Created by Caio on 07/04/2018.
 */

public abstract class FragmentBasic extends Fragment implements Interfaces.LayoutFragmentBasic {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate( getIdLayout() , container , false );
        getLayout().init( view );
        return view;
    }

}
