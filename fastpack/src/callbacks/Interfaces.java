package callbacks;


import callbacks.Interfaces.JustGetDados;
import sql.StringSql;

public interface Interfaces {
	UP
	public interface GetDados<T> extends JustGetDados {
		void setDados(PreparedStatement stmt) throws SQLException; 
		int getDados(T instanceOfObject , ResultSet rs) throws SQLException;
		String getNameTable();
	}
	
	
	public interface JustGetDados {
		boolean getDados(ResultSet rs) throws SQLException;
	}
	
	interface ObjectMethods <T> {
		void buscar(String where) throws  Exception;
		Collection<T> buscar(String where, String orderBy, String limit) throws Exception ;
	    Object inserir(StringSql.Insert insertSql) throws Exception;
	}
	

}
