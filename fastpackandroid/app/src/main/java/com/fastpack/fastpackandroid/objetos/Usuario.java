package com.fastpack.fastpackandroid.objetos;

import android.content.ContentValues;

import com.fastpack.fastpackandroid.base_dados.MyCursor;
import com.fastpack.fastpackandroid.base_dados.Script;

/**
 * Created by root on 28/03/18.
 */

public class Usuario extends ObjectBasicBaseDados<Usuario> {

    public static final int RESPONSE_LOGIN_ERROR = 473;

    public static final int TIPO_PRESTADOR = 1;
    public static final int TIPO_NORMAL    = 0;


    private int  tipo, status;
    private String cpf, password, nome;
    private Local local;

    public Usuario(){}

    private Usuario(MyCursor cursor ) {
        onDadosReceives( cursor );
    }

    @Override
    public void onDadosReceives(MyCursor cursor) {
        setId(      cursor.getInt( Script.Usuario.ID)  );
        setTipo(    cursor.getInt( Script.Usuario.TIPO ) );
        setStatus(  cursor.getInt( Script.Usuario.STATUS ) );
        setPassword(cursor.getString( Script.Usuario.PASSWORD ) );
        setCpf(     cursor.getString( Script.Usuario.CPF ) );
        setNome(    cursor.getString( Script.Usuario.NOME ) );
        setLocal(   new Local() );
        getLocal().setLatitude(  cursor.getDouble(Script.Usuario.LATITUDE) );
        getLocal().setLongitude( cursor.getDouble(Script.Usuario.LONGITUDE) );

    }

    @Override
    public ContentValues getContentValues(int type) {
        ContentValues values = new ContentValues();
        values.put( Script.Usuario.ID , getId() );
        values.put( Script.Usuario.CPF, getCpf() );
        values.put(Script. Usuario.LATITUDE,getLocal().getLatitude());
        values.put(Script. Usuario.LONGITUDE,getLocal().getLongitude());
        values.put(Script. Usuario.NOME, getNome() );
        values.put(Script. Usuario.PASSWORD,getPassword() );
        values.put(Script. Usuario.STATUS,getStatus() );
        values.put(Script. Usuario.TIPO,getTipo() );
        return values;

    }

    public boolean isPrestador() {
        return getTipo() == TIPO_PRESTADOR;
    }

    @Override
    public Usuario getDadosReceivs(MyCursor cursor) {
        return new Usuario( cursor );
    }

    @Override
    public String getNameTable() {
        return Script.Usuario.NAMETABLE;
    }

    @Override
    public String getWhereIdentificador() {
        if (getId() == 0) {
            return Script.Usuario.CPF + " = '" + getCpf() + "'";
        } else {
            return Script.Usuario.ID + " = " + getId();
        }
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
