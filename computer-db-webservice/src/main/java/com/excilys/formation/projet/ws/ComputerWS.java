package com.excilys.formation.projet.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Consumes("application/json")
@Produces("application/json")
public interface ComputerWS {
	@POST
	@Path("/readAll/")
	public List<String> readAll();
	
	@POST
	@Path("/helloWorld/")
	public String helloWorld();
}
