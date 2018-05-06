package com.fastpack.fastpackandroid.interfaces;

import com.fastpack.fastpackandroid.objetos.Address;

import org.jetbrains.annotations.Nullable;

public interface VivaCepListener {
    void whenSearchFinished(@Nullable Address address);
}

