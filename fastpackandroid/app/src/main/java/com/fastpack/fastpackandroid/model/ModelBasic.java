package com.fastpack.fastpackandroid.model;

/**
 * Created by thomas on 22/03/18.
 */

import android.support.annotation.Nullable;

import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.fastpack.fastpackandroid.requisicoes.Requisicoes;

import org.jetbrains.annotations.NotNull;


/**
 * Created by isaque on 14/03/18.
 */

public abstract class ModelBasic implements Interfaces.ModelBasic {


    public static final int RESPONSE_OK = 200;
    public static final int RESPONSE_INTERNAL_ERROR = 500;
    public static final int RESPONSE_NOT_FOUND = 404;
    public static final int NOT_CONNECTED = 235;
    public static final int RESPONSE_DESCONHECIDO = -1;

    private Interfaces.ModelUtils utils;
    public ModelBasic(@NotNull Interfaces.ModelUtils utils) {
        this.utils = utils;
    }

    Requisicoes requisicao;

    public Requisicoes getRequisicao() {
        return requisicao == null ? requisicao = new Requisicoes(this) : requisicao;
    }

    public Interfaces.ModelUtils getModel() {
        return utils;
    }
}
