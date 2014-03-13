package com.excilys.formation.projet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	
	static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);
	
	// @RequestHandler methods
	@RequestMapping("error")
	public String HandleErrors(){
		LOG.info("Error logged in!");
		return "error";
	}
	
	@RequestMapping("throwablerror")
	public String HandleThrowablErrors(){
		LOG.info("Throwable Error logged in!");
		return "error";
	}
}
