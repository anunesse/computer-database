package com.excilys.formation.projet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.excilys.formation.projet.om.domain.Computer;
import com.excilys.formation.projet.service.ComputerService;

@Controller
public class JacksonController {
	@Autowired
	ComputerService computerService;
	
	@RequestMapping(value="JSONWriter", method=RequestMethod.GET)
	public @ResponseBody List<Computer> readAll(){
		return computerService.read(0, 20, "ASC", "name", "");
	}
}
