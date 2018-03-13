package sql;

public class ConnectionSQLException extends Exception{

	public ConnectionSQLException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
	
	public ConnectionSQLException(String msg , Throwable erro) {
		super( msg , erro );
	}

}
