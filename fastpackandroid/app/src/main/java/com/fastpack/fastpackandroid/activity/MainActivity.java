package com.fastpack.fastpackandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fastpack.fastpackandroid.R;
import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.fastpack.fastpackandroid.layout.LayoutMainActivity;

public class MainActivity extends ActivityBasic {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
    }

    @Override
    public int getIdLayout() {
        return R.layout.activity_main;
    }

    @Override
    public Interfaces.LayoutBasicMethods getLayoutBasic() {
        return new LayoutMainActivity( this );
    }

    @Override
    public int getIdContainerView() {
        return R.id.container;
    }

}
