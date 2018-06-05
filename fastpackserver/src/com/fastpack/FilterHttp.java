package com.fastpack;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

public class FilterHttp {

	public FilterHttp(	@Context HttpHeaders headers ) {
		if(headers.getRequestHeaders().containsKey("Authorization")) {
			System.out.println("header containsKey");

			if( headers.getRequestHeaders().get("Authorization").get(0).equals("24CA%Gg8=+df@") ) {
				System.out.println("header is ok");
			}
		}
		
		System.out.println("httpHeader" + headers.getRequestHeaders().toString() );

	}


}
