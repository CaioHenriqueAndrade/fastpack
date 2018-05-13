package com.fastpack.fastpackandroid.objetos;

/**
 * Created by root on 28/03/18.
 */

public class UsuarioPrestador extends Usuario {

    private int raio;
    private int precoMedio;

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

    public int getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(int precoMedio) {
        this.precoMedio = precoMedio;
    }
}
