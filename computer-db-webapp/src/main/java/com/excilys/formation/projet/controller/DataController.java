package com.excilys.formation.projet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.projet.om.common.ComputerPage;
import com.excilys.formation.projet.om.common.OrderBy;
import com.excilys.formation.projet.om.domain.Computer;
import com.excilys.formation.projet.service.ComputerService;

@Controller
public class DataController {

	@Autowired
	private ComputerService computerService;

	// private IComputerDAO myComputerDAO;
	static final Logger LOG = LoggerFactory.getLogger(DataController.class);

	/**
	 * [GET] Display computer method. RequestMapping(value="Display", method =
	 * RequestMethod.GET).
	 * 
	 * @param search
	 * @param orderField
	 * @param order
	 * @param page
	 * @param display
	 * @param message
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "Display", method = RequestMethod.GET)
	public String displayComputer(
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "orderField", required = false) String orderField,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "display", required = false) String display,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "status", required = false) String status,
			ModelMap model) {
		
		int myDisplay;
		int myPage;
		
		Page<Computer> myWrap;

		if (display == null || display.isEmpty()) {
			myDisplay = 20;
		} else {
			myDisplay = Integer.parseInt(display);
		}

		if (page == null || page.isEmpty()) {
			myPage = 0;
		} else {
			myPage = Integer.parseInt(page);
		}

		OrderBy myOB;
		myOB = OrderBy.get(orderField, order);
		
		Pageable pageable = new ComputerPage(myPage, myDisplay, myOB);
		
		myWrap = computerService.retrievePage(pageable, search);
		
		model.addAttribute("page", page);
		model.addAttribute("search", search);
		model.addAttribute("orderby", myOB);
		
		model.addAttribute("wrap", myWrap);
		
		return "dashboard";
	}
}
