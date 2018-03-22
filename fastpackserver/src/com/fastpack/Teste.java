package com.fastpack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("teste")
public class Teste {

	@GET 
	public Response inventa() {
		
		return Response.ok("OI MUNDO NOVO").build();
		
		
		
	}
	
	
	
	
}
