package com.excilys.formation.projet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.projet.controller.wrapper.Page;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.ComputerService;

@Controller
public class DataController{

	@Autowired
	private ComputerService computerService;

	// private IComputerDAO myComputerDAO;
	static final Logger LOG = LoggerFactory.getLogger(DataController.class);

	/**
	 * [GET] Display computer method.
	 * RequestMapping(value="Display", method = RequestMethod.GET).
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
	@RequestMapping(value="Display", method = RequestMethod.GET)
	public String displayComputer(	@RequestParam(value = "search", required = false) String search,
									@RequestParam(value = "orderField", required = false) String orderField,
									@RequestParam(value = "order", required = false) String order,
									@RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "display", required = false) String display,
									@RequestParam(value = "message", required = false) String message,
									@RequestParam(value = "status", required = false) String status,
									ModelMap model){
		
		Page<Computer> myPage = new Page<Computer>();
		
		if(display == null || display.isEmpty()){
			myPage.setRecordsOnThisPage(20);
		}
		else{
			myPage.setRecordsOnThisPage(Integer.parseInt(display));
		}
		
		if(page == null || page.isEmpty()){
			myPage.setPageNumber(1);
		}
		else{
			myPage.setPageNumber(Integer.parseInt(page));
		}
		
		if(order == null || order.isEmpty()){
			myPage.setOrderDirection("ASC");
		}
		else if ("DESC".equals(order)){
			myPage.setOrderDirection(order);
		}
		else{
			myPage.setOrderDirection("ASC");
		}
		
		if(orderField == null || orderField.isEmpty()){
			myPage.setResultsOrderedBy("computer.name");
		}
		else if("computer.name".equals(orderField)
				|| "computer.company.name".equals(orderField)
				|| "computer.introduced".equals(orderField)
				|| "computer.discontinued".equals(orderField)){
			myPage.setResultsOrderedBy(orderField);
		}
		else{
			myPage.setResultsOrderedBy("computer.name");
		}
		
		if(search == null){
			myPage.setSearch("");
		}
		else{
			myPage.setSearch(search);
		}
		int offset = Math.max((myPage.getPageNumber() - 1) * myPage.getRecordsOnThisPage(), 0);
		myPage.setResults(computerService.read(myPage.getRecordsOnThisPage(), offset, myPage.getResultsOrderedBy(), myPage.getOrderDirection(), myPage.getSearch()));
		myPage.setTotalNumberOfRecords(computerService.readTotal(myPage.getSearch()));
		myPage.setNumberOfPages((int) Math.ceil(myPage.totalNumberOfRecords / myPage.recordsOnThisPage) + 1);
		
		LOG.debug(myPage.toString());
		model.addAttribute("pageData", myPage);
		return "dashboard";
	}
}
