package com.fastpack.fastpackandroid.base_dados;

import android.database.Cursor;

/**
 * Created by Caio on 21/01/2018.
 */

public class MyCursor implements BancoManager.MyCursorMethods {

    private Cursor cursor;
    public MyCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public String getString(String columnName) {
        int columIndex = cursor.getColumnIndex(columnName);

        if(columIndex == -1) throw new IllegalArgumentException("NOT IMPLEMENTED COLUMN " + columnName + " IN TABLE " );

        return cursor.getString( columIndex );
    }

    @Override
    public int getInt(String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    @Override
    public float getFloat(String columnName) {
        return cursor.getFloat(cursor.getColumnIndex(columnName));
    }

    void close() {
        cursor.close();
    }
}
