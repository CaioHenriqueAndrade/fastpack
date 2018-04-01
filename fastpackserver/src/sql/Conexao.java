package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Conexao {

	public Conexao() {
	}
	
	public Connection getConnection5() throws SQLException {

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
	
	public Connection getConnection() throws SQLException {
		
		DriverManager.registerDriver(new com.mysql.jdbc.Driver()); // Essa linha foi a diferença return


		String url ="jdbc:mysql://localhost:3306/fastpack";
		
        //String url = String.format("jdbc:mysql://%s/%s", host, database);

        /*
        String host = "servermysql.mysql.database.azure.com";
        String database = "events";
        String user = "uevents@servermysql";
        String password = "SenhaDataBase1";

        
        // Set connection properties.
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        properties.setProperty("useSSL", "true");
        properties.setProperty("verifyServerCertificate", "true");
        properties.setProperty("requireSSL", "false"); */

        // get connection
        return DriverManager.getConnection(url, "fastpack", "123456");
        
//        return DriverManager.getConnection(url, properties);
	}
	


}
