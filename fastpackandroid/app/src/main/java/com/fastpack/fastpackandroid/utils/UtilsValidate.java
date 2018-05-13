package com.fastpack.fastpackandroid.utils;

import org.jetbrains.annotations.NotNull;

public class UtilsValidate {

    public static boolean validateHora(@NotNull String text) {
        if( text.length() != 10) {
            return false;
        }

        boolean validar = text.substring(2,3).equals("/");

        if( !validar ) {
            return false;
        }

        validar = text.substring(5,6).equals("/");

        if( !validar ) {
            return false;
        }

        return true;
    }
}
