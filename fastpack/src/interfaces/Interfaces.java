package interfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.ws.rs.core.Response;

import sql.ConnectionSQLException;
import sql.StringSql;

public interface Interfaces {

	public interface GetDados<T> extends JustGetDados {
		void setDados(PreparedStatement stmt) throws SQLException,Exception; 
		int getDados(T instanceOfObject , ResultSet rs) throws SQLException, Exception;
		String getNameTable();
	}
	
	
	public interface JustGetDados {
		boolean getDados(ResultSet rs) throws SQLException, Exception;
	}

	
	public interface ResponseCallBack {
		Response getResponse() throws Exception , ConnectionSQLException ;
	}
	
	interface ObjectMethods <T> {
		void buscar(String where) throws  Exception;
		Collection<T> buscar(String where, String orderBy, String limit) throws Exception ;
	    Object inserir(StringSql.Insert insertSql) throws Exception;
	}
	
	interface ObjectMethodsNoSql <T> {
		void buscar(String where) throws  Exception;
		Collection<T> buscar(String where, String orderBy, String limit) throws Exception;
	    Object inserir(StringSql.Insert insertSql) throws Exception;
	}

}
