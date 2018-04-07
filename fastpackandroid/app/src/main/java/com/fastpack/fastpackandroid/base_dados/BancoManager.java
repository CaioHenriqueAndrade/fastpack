package com.fastpack.fastpackandroid.base_dados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Caio on 13/01/2018.
 */

public class BancoManager {
    private SQLiteDatabase db;
    private CreateDataBase banco;

    public BancoManager(Context c) {
        banco = new CreateDataBase( c );
    }

    public int inserir(String nameTable, ContentValues values) {
        int resultado;

        db = banco.getWritableDatabase();
        resultado = (int) db.insert(nameTable, null, values);
        db.close();
        return resultado;
    }

    public boolean altera(String nameTable, ContentValues values, String where) {
        db = banco.getWritableDatabase();
        int linha = db.update(nameTable, values, where, null);
        db.close();
        return linha > 0;
    }

    public void excluir(String nameTable, String where) {
        try {
            db = banco.getWritableDatabase();
            db.delete(nameTable, where, null);
            db.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean busca(String nameTable, String where ,final GetDados object) {
        return busca(nameTable, where, null, null, new GetDados() {
            @Override
            public void onDadosReceives(MyCursor cursor) {
                object.onDadosReceives( cursor );
            }
        });
    }


    //retorna true se achou dados na tablea
    public boolean busca(String nameTable, String where, String orderBy, String limit , GetDados getDados) {
        boolean retorno = false;

        db = banco.getReadableDatabase();
        Cursor cursorSql = db.query(nameTable, null, where, null, null, null, orderBy, limit);
        if (cursorSql.getCount() > 0) { //se tiver dados na tabela
            MyCursor cursor = new MyCursor( cursorSql );
            retorno = true;
            cursorSql.moveToFirst(); // vai mover pro primeiro
            do {
                getDados.onDadosReceives( cursor );
            } while (cursorSql.moveToNext()); //Condição para parar de pegar, quando acabar os dados
        }
        cursorSql.close();
        db.close();
        return retorno;
    }

    public interface GetDados {
        void onDadosReceives(MyCursor cursor);
    }


    public interface OnAllSql<T> extends GetDados{
        ContentValues getContentValues(int type);
        void inserir(BancoManager bancoManager);
        void excluir(BancoManager bancoManager);
        boolean alterar(BancoManager bancoManager, int alteracaoType);
         boolean buscar(BancoManager bancoManager, String where);
        void alterarIfNotExistsInserir(BancoManager bancoManager);
        List<T> buscar(BancoManager bancoManager, String where, String orderBy, String limit);
    }

    interface MyCursorMethods {
        float getFloat(String columnName);
        int getInt(String columnName);
        String getString(String columnName);
    }
}

