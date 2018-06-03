package com.fastpack.fastpackandroid.utils;

import com.fastpack.fastpackandroid.objetos.Address;
import com.fastpack.fastpackandroid.objetos.Local;

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


    public static int getTaxaEntrega( int taxaPorKm , Local local1, Local local2 ) {

        double distance = getDistance( local1 , local2 );

        if( distance < 1) {
            return taxaPorKm ;
        } else {
            return (int) (distance * taxaPorKm) ;
        }
    }

    public static double getDistance(Local local1, Local local2) {
        return getDistance( local1.getLatitude() , local1.getLongitude() , local2.getLatitude() ,local2.getLongitude() , Unit.KILOMETERS );
    }

    public static double getDistance(double lat1, double lon1, double lat2, double lon2, Unit u) {
        String unit = u.toString();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::    This function converts decimal degrees to radians                         :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::    This function converts radians to decimal degrees                         :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    public enum Unit {
        KILOMETERS("Kilometers\n"),MILES("Miles\n"),NAUTICAL_MILES("Nautical Miles\n");

        String unit;
        Unit(String unit) {
            this.unit = unit;
        }

        public String toString() {
            return unit;
        }
    }

}
