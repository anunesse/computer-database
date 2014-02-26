package com.excilys.formation.projet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.projet.controller.wrapper.Page;
import com.excilys.formation.projet.om.Log;
import com.excilys.formation.projet.services.LogService;

@Controller
public class LogsController{
	@Autowired
	private LogService logService;

	public LogsController() {
		super();
	}

	@RequestMapping(value="/Logs", method = RequestMethod.GET)
	protected String doGet(Model model, HttpServletRequest request){
		Page<Log> myPage = new Page<Log>();
		myPage.results = logService.readAll();
		request.setAttribute("pageData", myPage);
		/*request.getRequestDispatcher("WEB-INF/logs.jsp").forward(request,
				response);*/
		return "logs";
	}
}
