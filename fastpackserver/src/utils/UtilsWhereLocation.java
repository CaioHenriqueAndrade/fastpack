package utils;

import java.util.ArrayList;
import java.util.List;

public class UtilsWhereLocation {

	public UtilsWhereLocation() { }
	
	public static StringBuilder getSelectStringOfCoordenadas(String latitude, String longitude, double margem) {
		return returnSelectLoation(  getCoordenadas( latitude , longitude , margem )  );
	}
	
	
    private static List<Double> getCoordenadas(String latitude, String longitude, double Margem) {
		/*
		 * Procura coordenadas através dos parametros.... Serve para busca e comparações
		 * se false, retorna 4 valores com as margens para o update se true retorna os 8
		 * valores a serem comparados.
		 *
		 */

        List<Double> RealizacaoDoUpdate = new ArrayList<>();

        double LongMore = Double.parseDouble(longitude) + Margem;
        double LongLess = Double.parseDouble(longitude) - Margem;

        double LatMore = Double.parseDouble(latitude) + Margem;
        double LatLess = Double.parseDouble(latitude) - Margem;

        RealizacaoDoUpdate.add(LatMore);
        RealizacaoDoUpdate.add(LatLess);
        RealizacaoDoUpdate.add(LongMore);
        RealizacaoDoUpdate.add(LongLess);

        return RealizacaoDoUpdate;
    }

    private static StringBuilder returnSelectLoation(List<Double> listaDouble) {

        StringBuilder select = new StringBuilder();

        select.append(" latitude ");
        if (listaDouble.get(0) < 0) {
            select.append("> ").append( listaDouble.get(1) ).append(" and latitude < ").append( listaDouble.get(0) ).append(" AND Longitude");
        } else {
            select.append("< ").append( listaDouble.get(0) ).append(" and latitude > ").append( listaDouble.get(1) ).append(" AND Longitude");
        }

        if (listaDouble.get(2) < 0) {
            select.append(" > ").append( listaDouble.get(3) ).append(" and Longitude < ").append( listaDouble.get(2) );
        } else {
            select.append(" < ").append( listaDouble.get(2) ).append(" and Longitude > ").append( listaDouble.get(3) );
        }

        return select;
    }


    public static boolean isDouble(String db) {
        try {
            Double.parseDouble( db );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	
}
