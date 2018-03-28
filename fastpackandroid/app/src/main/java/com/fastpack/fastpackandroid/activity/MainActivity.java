package com.fastpack.fastpackandroid.activity;

import android.os.Bundle;

import com.fastpack.fastpackandroid.R;
import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.fastpack.fastpackandroid.layout.LayoutMainActivity;

public class MainActivity extends ActivityBasic {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public Interfaces.LayoutBasicMethods getLayoutBasic() {
        return new LayoutMainActivity();
    }

    @Override
    public int getIdContainerView() {
        return 0;
    }

}
