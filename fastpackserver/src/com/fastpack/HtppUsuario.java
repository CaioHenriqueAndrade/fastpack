package com.fastpack;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import exception.IllegalLoginException;
import interfaces.Interfaces;
import model.ModelUsuario;
import model.ModelUsuarioPrestador;
import sql.ConnectionSQLException;
import objetos.Usuario;
import objetos.UsuarioPrestador;
import utils.ResponseManager;

@Path("usuario")
public class HtppUsuario {
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response novoUsuario(String json) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {

			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				Usuario usuario = new Gson().fromJson( json , Usuario.class );
				new ModelUsuario( usuario ).inserir( true );
				return Response.ok().build();
			}
			
		});
	}
	

	//RETORNA O LOGIN DO USUARIO VIA CPF E SENHA
	@GET
	@Path("{cpf}/{senha}")
	public Response tentarLogar(@PathParam("cpf") String cpf , @PathParam("senha") String senha) {
		return new ResponseManager().execute(new Interfaces.ResponseCallBack() {

			@Override
			public Response getResponse() throws Exception, ConnectionSQLException {
				try {
					Usuario usuario = new ModelUsuario().tryBuscarUsuario( cpf , senha );
					
					//se buscou com sucesso...
					//vamos ver se e prestador para buscarmos todos os dados dele...
					if( usuario.userIsPrestador() ) {
						ModelUsuarioPrestador model = new ModelUsuarioPrestador( new UsuarioPrestador( usuario ) );
						model.buscar( usuario.getId() );
						usuario = model.getUsuarioPrestador();
					}
					
					return Response.ok( new Gson().toJson( usuario ) ).build();
				} catch (IllegalLoginException e) {
					//as outras execoes seram tratadas na camada acima com a maneira padrao
					return Response.serverError().status( Usuario.RESPONSE_LOGIN_ERROR ).build();
				}
			}
			
		});
	}
	
}
