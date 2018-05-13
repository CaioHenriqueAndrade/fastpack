package sql;

import java.util.ArrayList;
import java.util.List;

import objetos.Local;

public class StringSql {

	public static class WhereUtils {
		
		public static String getInnerJoin( String nameTable, String nameTable2 , String column1, String column2 ) {
			return new StringBuilder().append( " inner join " )
					.append( nameTable ).append(" on ").append( appendNameTableWithColumn( nameTable , column1 ) )
					.append(" = ").append( appendNameTableWithColumn( nameTable2, column2 ) ).toString();
		}
		
		public static String appendNameTableWithColumn(String nameTable, String column) {
			return new StringBuilder().append( nameTable ).append(".").append( column ).toString();
		}
		

		public static StringBuilder getSelectStringOfCoordenadas(Local local) {
			return returnSelectLoation(  getCoordenadas( local.getLatitude() , local.getLongitude() , 0.5 )  );
		}
		
		public static StringBuilder getSelectStringOfCoordenadas(double latitude, double longitude, double margem) {
			return returnSelectLoation(  getCoordenadas( latitude , longitude , margem )  );
		}
		
		
	    private static List<Double> getCoordenadas(double latitude, double longitude, double Margem) {
			/*
			 * Procura coordenadas através dos parametros.... Serve para busca e comparações
			 * se false, retorna 4 valores com as margens para o update se true retorna os 8
			 * valores a serem comparados.
			 *
			 */

	        List<Double> RealizacaoDoUpdate = new ArrayList<>();

	        double LongMore =(longitude) + Margem;
	        double LongLess = (longitude) - Margem;

	        double LatMore = (latitude) + Margem;
	        double LatLess = (latitude) - Margem;

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
	}
	
	
	
	public static class Insert {
		StringBuilder stringBuilder = new StringBuilder();
		int numberOfParams = 0;

		public Insert(String nameTable) {
			stringBuilder.append("insert into ").append(nameTable).append(" ( ");
		}

		public void addColumn(String nameColum) {
			numberOfParams++;
			
			if (numberOfParams != 1)
				stringBuilder.append(",").append(nameColum);
			else
				stringBuilder.append(nameColum);
			
		}

		public String build() {

			stringBuilder.append( " ) values ( ");
			for(int i = 0 ; i != numberOfParams ; i++) {
				if(i == 0)
						stringBuilder.append("?");
					else
						stringBuilder.append(",?");
			}
			return stringBuilder.append( " ) ").toString();
		}
	}
	public static class Update {

		private StringBuilder criandoTabela = new StringBuilder();

		public Update(String nameTable) {
			verifica = false;
			criandoTabela.append("UPDATE ").append( nameTable ).append(" SET ");

		}

		public void add(String nameAtributo) {

			if (verifica == false) {
				verifica = true;
				criandoTabela.append(" ").append( nameAtributo ).append(" = ? ");
			} else {
				criandoTabela.append(", ").append(nameAtributo).append(" = ? ");
			}

		}

		public String build(String where) {

			return criandoTabela.append(" where ").append(where).toString();

		}

		private boolean verifica = false;
	}
}
