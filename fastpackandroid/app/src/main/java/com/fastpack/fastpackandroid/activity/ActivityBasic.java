package com.fastpack.fastpackandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import com.fastpack.fastpackandroid.R;
import com.fastpack.fastpackandroid.interfaces.Interfaces;

/**
 * Created by root on 28/03/18.
 */

public abstract class ActivityBasic extends AppCompatActivity implements Interfaces.ActivityBasicMethods {
    private boolean isVisible = false;
    private boolean alreadyDestroyed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isVisible = true;
        super.onCreate(savedInstanceState);
    }

    public void setActionBar(Toolbar toolbar) {
        super.setActionBar( toolbar );
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getLayoutBasic().init( findViewById( getIdContainerView() ) );
    }

    @Override
    protected void onResume() {
        isVisible = true;
        super.onResume();
    }


    @Override
    protected void onStop() {
        isVisible = false;
        super.onStop();
    }

    @Override
    public boolean isAlreadyDestroyed() {
        return alreadyDestroyed;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

}
