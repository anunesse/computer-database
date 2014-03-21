package com.excilys.formation.projet.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.om.domain.Computer;
import com.excilys.formation.projet.service.ComputerService;
import com.excilys.formation.projet.ws.ComputerWS;


@Service("computerWS")
public class ComputerWSImpl implements ComputerWS{

	@Autowired
	ComputerService computerService;
	
	public List<String> readAll() {
		List<String> myList = new ArrayList<String>();
		for(Computer c : computerService.read(0, 20, "ASC", "computer.name", "")){
			myList.add(c.toString());
		}
		return myList;
	}

	public String helloWorld() {
		return "Hello Dude!";
	}

}
