package com.fastpack;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import interfaces.Interfaces;
import model.ModelUsuarioPrestador;
import objetos.Local;
import objetos.UsuarioPrestador;
import sql.ConnectionSQLException;
import utils.ResponseManager;

@Path("prestador")
public class HttpUsuarioPrestador {

	
	//ele diz as latitudes e longitude que ele esta e o sistema busca proximo a ele
	//usuarios prestadores...
	@GET
	@Path("{latitude}/{longitude}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tentarLogar(@PathParam("latitude") String latitude , @PathParam("longitude") String longitude) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {

			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				ModelUsuarioPrestador model = new ModelUsuarioPrestador();
				
				Collection<UsuarioPrestador> list = model.buscarProximos( new Local( latitude , longitude ) );
				
				return Response.ok( new Gson().toJson( list ) ).build();
			}
			
		});
	}
}
