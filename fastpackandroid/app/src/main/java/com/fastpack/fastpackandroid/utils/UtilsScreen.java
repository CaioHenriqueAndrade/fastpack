package com.fastpack.fastpackandroid.utils;

import android.content.res.Resources;

import com.fastpack.fastpackandroid.R;

/**
 * Created by Caio on 07/04/2018.
 */

public class UtilsScreen {

    public static boolean isTablet(Resources res) {
        return res.getBoolean( R.bool.isTablet );
    }
}
