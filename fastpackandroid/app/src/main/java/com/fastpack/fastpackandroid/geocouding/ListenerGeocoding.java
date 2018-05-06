package com.fastpack.fastpackandroid.geocouding;

import android.support.annotation.Nullable;

import com.fastpack.fastpackandroid.objetos.Address;

/**
 * Created by Caio on 28/01/2018.
 */

public interface ListenerGeocoding {
    void onFinishProcess(@Nullable Address address);
}
