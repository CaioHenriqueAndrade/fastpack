package com.fastpack;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import interfaces.Interfaces;
import sql.ConnectionSQLException;
import utils.ResponseManager;

@Path("usuario")
public class HtppUsuario {
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response novoUsuario() {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {

			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {

				
				return null;
			}
			
		});
	}
}
