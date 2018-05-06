package com.fastpack.fastpackandroid.objetos;

import android.content.ContentValues;

import com.fastpack.fastpackandroid.base_dados.BancoManager;
import com.fastpack.fastpackandroid.base_dados.MyCursor;
import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjectBasicBaseDados<T> extends ObjectBasic implements BancoManager.OnAllSql<T> , Interfaces.ObjectBasicMethods<T> {

    public static final int CONTENT_VALUES_TYPE_ALL = 0;

    public ObjectBasicBaseDados() {

    }

    public ObjectBasicBaseDados(MyCursor cursor) {
        onDadosReceives(cursor);
    }

    @Override
    public void inserir(BancoManager bancoManager) {
        bancoManager.inserir(getNameTable(), getContentValues(CONTENT_VALUES_TYPE_ALL));
    }


    @Override
    public void excluir(BancoManager bancoManager) {
        bancoManager.excluir(getNameTable(), getWhereIdentificador());
    }

    @Override
    public boolean alterar(BancoManager bancoManager, int alteracaoType) {
        return bancoManager.altera(getNameTable(), getContentValues(alteracaoType), getWhereIdentificador());
    }

    @Override
    public boolean buscar(BancoManager bancoManager, String where) {
        return bancoManager.busca(getNameTable(), where, this);
    }

    @Override
    public synchronized void alterarIfNotExistsInserir(BancoManager bancoManager) {
        ContentValues values = getContentValues(CONTENT_VALUES_TYPE_ALL);
        if (!bancoManager.altera(getNameTable(), values, getWhereIdentificador())) {
            //se nao alterou insere..
            setId(  bancoManager.inserir(getNameTable(), values) );
        }
    }

    //a unica funcao desta lista e auxiliar nas buscas da base de dados.....
    @Expose(serialize = false, deserialize = false)
    private List<T> lista;

    @Override
    public List<T> buscar(BancoManager bancoManager, String where, String orderBy, String limit) {
        lista = new ArrayList<>();

        bancoManager.busca(getNameTable(), where, orderBy, limit, new BancoManager.GetDados() {
            @Override
            public void onDadosReceives(MyCursor cursor) {
                lista.add(getDadosReceivs(cursor));
            }
        });

        return lista;
    }
}