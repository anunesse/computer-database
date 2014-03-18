package com.excilys.formation.projet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.projet.service.LogService;

@Controller
public class LogsController{
	@Autowired
	private LogService logService;

	public LogsController() {
		super();
	}

	@RequestMapping(value="/Logs", method = RequestMethod.GET)
	protected String doGet(Model model){	
		model.addAttribute("pageData", logService.readAll());
		return "logs";
	}
}
