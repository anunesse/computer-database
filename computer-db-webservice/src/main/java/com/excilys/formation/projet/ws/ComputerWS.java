package com.excilys.formation.projet.ws;

import java.util.List;

import javax.jws.WebService;

@WebService
public interface ComputerWS {
	public List<String> readAll();
	public String helloWorld();
}
