package com.fastpack.fastpackandroid.base_dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Caio on 13/01/2018.
 */

public class CreateDataBase extends SQLiteOpenHelper {


    private static final String NOME_BANCO = "fastpack";
    private static final int VERSAO = 1;

    public CreateDataBase(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( getTableUsuario() );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static String getTableUsuario() {
        CreateTableHelper helperTable = new CreateTableHelper( Script.Usuario.NAMETABLE );
        helperTable.addAtributo( Script.Usuario.ID          , "integer primary key autoincrement" );
        helperTable.addAtributo( Script.Usuario.LATITUDE    , "double");
        helperTable.addAtributo( Script.Usuario.LONGITUDE   , "double");
        helperTable.addAtributo( Script.Usuario.NOME        , "text");
        helperTable.addAtributo( Script.Usuario.PASSWORD    , "text");
        helperTable.addAtributo( Script.Usuario.STATUS      , "text");
        helperTable.addAtributo( Script.Usuario.TIPO   , "text");

        return helperTable.build();
    }


}
