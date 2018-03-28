package callbacks;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.mysql.jdbc.PreparedStatement;

import sql.StringSql;

public interface Interfaces {
	
	public interface GetDados<T> extends JustGetDados {
		void setDados(PreparedStatement stmt) throws SQLException; 
		int getDados(T instanceOfObject , ResultSet rs) throws SQLException;
		String getNameTable();
	}
	
	
	public interface JustGetDados {
		boolean getDados(ResultSet rs) throws SQLException;
	}
	
	interface ObjectMethods <T> {
		Class<T> getClassTypeT();
		void buscar(String where) throws  Exception;
		Collection<T> buscar(String where, String orderBy, String limit) throws Exception ;
	    Object inserir(StringSql.Insert insertSql) throws Exception;
	}
	

}
