package com.fastpack;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import interfaces.Interfaces;
import model.ModelPedido;
import objetos.Pedido;
import sql.ConnectionSQLException;
import utils.ResponseManager;

@Path("pedido")
public class HttpPedido {

	@Context private HttpServletRequest request;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response novoPedido(String json) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {

			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				Pedido pedido = new Gson().fromJson( json , Pedido.class );
				new ModelPedido( pedido ).inserir( true );
				
				return Response.ok().build();
			}
			
		});
	}

	@GET
	@Path("{idUser}/{ultimoCarregado}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPedidos(@PathParam("idUser") String idUser , @PathParam("ultimoCarregado") String ultimoCarregado ) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {

			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				Collection<Pedido> collection = new ModelPedido().buscaBy( idUser, ultimoCarregado , true );
				return Response.ok( new Gson().toJson( collection ) ).build();
			}
			
		});
	}
	
	
	@GET
	@Path("prestador/{idUser}/{ultimoCarregado}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPedidosPrestador(@PathParam("idUser") String idUser , @PathParam("ultimoCarregado") String ultimoCarregado ) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {
			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				Collection<Pedido> collection = new ModelPedido().buscaBy( idUser, ultimoCarregado , false );
				return Response.ok( new Gson().toJson( collection ) ).build();
			}
			
		});
	}
	
	
}
