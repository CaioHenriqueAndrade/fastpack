package com.fastpack.fastpackandroid.requisicoes;

/**
 * Created by thomas on 22/03/18.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.fastpack.fastpackandroid.model.ModelBasic;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaque on 14/03/18.
 */

public class Requisicoes {

    public static final String urlBase = "http://192.168.1.150:8080/fastpackserver/rest/";

    private Interfaces.ModelBasic modelBasic;

    public Requisicoes(Interfaces.ModelBasic modelBasic) {
        this.modelBasic = modelBasic;
    }

    //chama o get com nossa url base + paths passadas separadas por /
    public <T> void get(int param , T tipoClasse , String... paths) {

        String json = get( param , paths);

        if(json == null) return;

        T tipo = onPostRequisicaoGet( json, tipoClasse);

        getModel().onDadosReceives( param , tipo , ModelBasic.RESPONSE_OK );
    }

    //chama o get com toda path completa ja
    public <T> void get(int param , String path, T tipoClasse) {

        String json = getAllPath(param , path);

        if(json == null) return;

        T t = onPostRequisicaoGet( json, tipoClasse);

        getModel().onDadosReceives( param , t , ModelBasic.RESPONSE_OK );


    }

    private <T> T onPostRequisicaoGet(String json, T tipoClasse) {
        if (json == null) return null;

        Gson g = new Gson();
        return (T) g.fromJson(json, tipoClasse.getClass());
    }

    public <T> void getList(int param , Class<T> clazz, String... paths) {
        getLista(param , clazz, paths);
    }

    private <T> void getLista(int param , Class<T> clazz, String... paths) {

        String json = get(param , paths);
        tooList(param , json , clazz);
    }

    private <T> void tooList(int param , String json , Class<T> clazz) {
        if (json == null) return;//erro ja foi tratado

        Gson g = new Gson();
        //Type collectionType = new TypeToken<List<T>>(){}.getType();

        JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
        if (arry == null) return;

        List<T> list = new ArrayList<>();

        for (JsonElement jsonElement : arry) {
            list.add(g.fromJson(jsonElement, clazz));
        }

        //List<T> array = g.fromJson(json, collectionType );

        getModel().onDadosReceives( param ,list , ModelBasic.RESPONSE_OK);

    }

    private String get(int param , String... params) {
        return getAllPath(param ,toPath(params));
    }

    private String toPath(String... params) {
        StringBuilder stringBuilder = new StringBuilder(urlBase);

        for (int i = 0; i < params.length; i++) {

            stringBuilder.append(params[i]);

            if (i < params.length - 1) {
                stringBuilder.append("/");

            }
        }
        return stringBuilder.toString();
    }

    @Nullable
    private String getAllPath(int param , String allPath) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        int responseCod = ModelBasic.RESPONSE_DESCONHECIDO;
        try {

            URL url = new URL(allPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout( 4000 );
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (!isValid( param , urlConnection)) {
                return null;
            }


            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                return lerBuilder(reader);
            } else {
                getModel().onDadosReceives( param , null ,  responseCod );
            }

        } catch (Exception var18) {
            var18.printStackTrace();
            getModel().onDadosReceives( param , null , ModelBasic.NOT_CONNECTED);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }
        }
        return null;
    }

    public <T,X> void requerer(int param ,String requisicaoMethod, T objectAserEnviado ,
                               @Nullable X objectAserRecebido, boolean getInputStream, String... paths) {
        requerer(param ,requisicaoMethod , toPath( paths ) , objectAserEnviado , objectAserRecebido , getInputStream );
    }
    public <T,X> void requererList(int param , String requisicaoMethod, T objectAserEnviado ,
                                   @NonNull X objectAserRecebido, boolean getInputStream, String... paths) {
        requererList(param , toPath( paths ) , objectAserEnviado , objectAserRecebido.getClass() , requisicaoMethod , getInputStream );
    }
    public <T,X> void requerer(int param ,String allPath , T objectAserEnviado ,
                               @Nullable X objectAserRecebido,String requisicaoMethod  , boolean getInputStream ) {
        requerer(param ,requisicaoMethod , allPath , objectAserEnviado , objectAserRecebido , getInputStream );
    }
    //o callback chama ele
    public <T,O> void requererList(int param , String allPath , T objectAserEnviado , Class<O> objectAserRecebido ,String requisicaoMethod  , boolean getInputStream) {
        String json = getJson(param , requisicaoMethod , allPath , objectAserEnviado , getInputStream );
        if(json != null)
            tooList( param , json , objectAserRecebido );
    }

    private <T,X> void requerer(int param ,String RequisicaoMethod, String urlPath, T objectAserEnviado ,X objectAserRecebido , boolean getInputStream  ) {

        String json = getJson( param , RequisicaoMethod , urlPath , objectAserEnviado , getInputStream );

        if( objectAserRecebido == null) return;

        X recebido = onPostRequisicaoGet( json , objectAserRecebido );

        if(recebido == null) return;

        getModel().onDadosReceives( param , recebido , ModelBasic.RESPONSE_OK );
    }

    private boolean isValid(int param , HttpURLConnection urlConnection) throws IOException {
        if (urlConnection.getResponseCode() != 200) {
            modelBasic.onDadosReceives(param, urlConnection.getResponseMessage() , urlConnection.getResponseCode() );
            return false;
        }
        return true;
    }

    private static String lerBuilder(BufferedReader reader) throws IOException {
        String linha;
        StringBuilder buffer = new StringBuilder();
        while ((linha = reader.readLine()) != null) {
            buffer.append(linha);
        }
        return buffer.toString();
    }

    public Interfaces.ModelBasic getModel() {
        return modelBasic;
    }

    private <T> String getJson(int  param ,String RequisicaoMethod, String urlPath, T objectAserEnviado , boolean getInputStream ) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        int responseCod = ModelBasic.RESPONSE_DESCONHECIDO;
        try {

            Gson g = new Gson();
            URL url = new URL(urlPath);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("Content-type", "application/json"); //Propriedades da requisição
            urlConnection.setRequestMethod(RequisicaoMethod); // Requisição
            urlConnection.setReadTimeout(4000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setDoOutput(true); //fala que voce vai enviar algo
            urlConnection.setDoInput(true); // e que receberá algo
            urlConnection.setChunkedStreamingMode(0); ///Para melhor performânce, controla a memória
            //digo que não sei o tamanho do arquivo e pode ser grande

            PrintStream printStream = new PrintStream(urlConnection.getOutputStream());
            printStream.println(g.toJson(objectAserEnviado)); //seta o que voce vai enviar
            printStream.flush();
            printStream.close();
            urlConnection.connect(); //envia para o servidor

            responseCod =  urlConnection.getResponseCode();

            if( !isValid( param , urlConnection ) ) {
                //se o responseCode foi diferente de 200
                getModel().onDadosReceives(  param , null , responseCod );
                return null;
            }

            if(!getInputStream ) {
                getModel().onDadosReceives( param , null , responseCod );
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                getModel().onDadosReceives( param , null , responseCod );
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            return lerBuilder(reader);
        } catch (IOException e) {
            e.printStackTrace();
            getModel().onDadosReceives( param , null , responseCod );
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}