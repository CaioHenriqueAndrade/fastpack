package com.fastpack.fastpackandroid.objetos;

import com.google.gson.Gson;

/**
 * Created by root on 28/03/18.
 */

public class ObjectBasic {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toJson() {
        return new Gson().toJson( this );
    }

}
