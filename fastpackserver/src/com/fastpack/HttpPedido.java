package com.fastpack;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import interfaces.Interfaces;
import model.ModelPedido;
import objetos.Local;
import objetos.Pedido;
import objetos.PedidoStatus;
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
	@Path("atualizar/{idUser}/{idpedido}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPedido(@PathParam("idUser") String idUser , @PathParam("idpedido") String idPedido ) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {

			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				Pedido pedido = ModelPedido.getPedido( Integer.parseInt( idUser ) , idPedido ); 
				return Response.ok( new Gson().toJson( pedido ) ).build();
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
	/******
	 * Recebe um json de PedidoStatus com as infos que devem ser trocadas
	 * 
	 */
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterarPedido(String json) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {

			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				PedidoStatus pedido = new Gson().fromJson( json , PedidoStatus.class );
				new ModelPedido().alterar(pedido);
				return Response.ok().build();
			}
			
		});
	}
	
	@GET
	@Path("proximos/{latitude}/{longitude}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPedidosProximos(
			@PathParam("latitude") String latitude , 
			@PathParam("longitude") String longitude ) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {
			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				
				Local local = new Local( latitude , longitude );
				
				Collection<Pedido> collection = new ModelPedido().buscarPedidos(local);
				
				return Response.ok( new Gson().toJson( collection ) ).build();
			}
		});
	}
	
}
