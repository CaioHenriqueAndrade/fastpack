package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Conexao {

	public Conexao() {
		// TODO Auto-generated constructor stub
	}
	
	public Connection getConnection() throws SQLException {

        String hostName = "localhost";
        String dbName = "events";
        String user = "caio";
        String password = "1234";

	
		/*
		String hostName = "uevents.database.windows.net";
        String dbName = "uevents";
        String user = "uevents@uevents";
        String password = "SenhaDataBase1";
        */
		try {
			//DriverManager.registerDriver(new com.mysql.jdbc.Driver()); // Essa linha foi a diferença return
	        Object o = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	        

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException("Sem o driver de conexao");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	     // Connect to database
		//producao
        //String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=brazilsouth1-a.control.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);

		
		String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;loginTimeout=8;", hostName, dbName, user, password);
		

		// Initialize connection object
		try {

			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new SQLException("Failed to create connection to database.", e);
		}

	}

}
