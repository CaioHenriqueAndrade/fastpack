package com.fastpack.fastpackandroid.utils;

import com.fastpack.fastpackandroid.objetos.Address;

import org.jetbrains.annotations.NotNull;

public class UtilsConvert {

    @NotNull
    public static String dateToDataMysql(@NotNull String data) {
        String dias = data.substring(0,2);
        String mes = data.substring(3,5);
        String ano = data.substring(6,10);

        return ano + "-" + mes + "-" + dias;
    }

    public static String dataMysqlToData(String data) {
        String ano = data.substring(0,4);
        String mes = data.substring(5,7);
        String dias = data.substring(8,10);
        return dias + "/" + mes + "/" + ano;
    }

    public static String toPreco(int preco) {
        String valor = Integer.toString(preco);
        int sizeAtual = valor.length();
        return sizeAtual > 2 ? valor.substring(0, sizeAtual - 2) + "," + valor.substring(sizeAtual - 2, sizeAtual):"0," + preco;
    }

    public static int toPreco(int preco, @NotNull Address addressEntrega, @NotNull Address addressRetirada) {
        return preco;
    }
}
