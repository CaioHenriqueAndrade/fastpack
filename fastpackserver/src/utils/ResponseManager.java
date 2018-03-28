package utils;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import interfaces.Interfaces;
import sql.ConnectionSQLException;

public class ResponseManager {
	public static final int ERROR_CONNECTION_SQL = 376; 
	public static final int ERROR_SQL_SYNTAX = 345; 
	public static final int ERROR_INTERNAL = 326; 

	public ResponseManager() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Response execute(Interfaces.ResponseCallBack callback) {
		
		try {
			return callback.getResponse();
		} catch (ConnectionSQLException e) {
			
			onError(e);
			return Response.serverError().status(ERROR_CONNECTION_SQL).entity("N�o temos conex�o para salvar os dados no servidor").build();
		} catch(SQLException e) {
			
			onError(e);
			return Response.serverError().status(ERROR_SQL_SYNTAX).entity("N�o obtimos sucesso ao salvar no servidor, revise os dados!").build();
		} catch (Exception e) {

			onError(e);
			return Response.serverError().status(ERROR_INTERNAL).entity("Ocorreu um erro interno no servidor").build();
		}
	}

	private void onError(Exception e) {
		e.printStackTrace();
	}

}
