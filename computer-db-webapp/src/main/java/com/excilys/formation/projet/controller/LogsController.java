package com.excilys.formation.projet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.projet.controller.wrapper.Page;
import com.excilys.formation.projet.om.Log;
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
		Page<Log> myPage = new Page<Log>();
		myPage.results = logService.readAll();
		myPage.totalNumberOfRecords = myPage.results.size();
		myPage.setPageNumber(1);
		myPage.setRecordsOnThisPage(20);
		myPage.setNumberOfPages((int) Math.ceil(myPage.totalNumberOfRecords / myPage.recordsOnThisPage) + 1);
		model.addAttribute("pageData", myPage);
		return "logs";
	}
}
