package com.fastpack.fastpackandroid.base_dados;

/**
 * Created by Caio on 24/01/2018.
 */

public class CreateTableHelper {

    public String script_tabelas;

    public CreateTableHelper(String NomeDaTabela) {
        script_tabelas = "CREATE TABLE " + NomeDaTabela + " ( ";
    }

    private boolean verif = false;

    public void addAtributo(String name, String tipagem) {
        if (script_tabelas == null)
            throw new IllegalArgumentException("ILEGAL ARGUMENT EXCEPTION BEFORE CREATETABLE PLIS");

        if (!verif) {

            script_tabelas += " \n " + name + " " + tipagem + " ";
            verif = true;
            return;
        }

        script_tabelas += ", \n " + name + " " + tipagem + " ";

    }

    public String build() {
        return script_tabelas += " );";
    }

}
