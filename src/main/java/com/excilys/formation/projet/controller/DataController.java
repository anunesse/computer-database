package com.excilys.formation.projet.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.projet.controller.wrapper.Page;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.services.ComputerService;

@Controller
public class DataController{

	@Autowired
	private ComputerService computerService;

	// private IComputerDAO myComputerDAO;
	static final Logger LOG = LoggerFactory.getLogger(DataController.class);

	/**
	 * [GET] Display computer method.
	 * RequestMapping(value="Display", method = RequestMethod.GET).
	 * @param request
	 * @return
	 */
	@RequestMapping(value="Display", method = RequestMethod.GET)
	public String displayComputer(HttpServletRequest request){
		
		Page<Computer> myPage = new Page<Computer>();
		//request.addAllAttributes()
		
		if (request.getParameterMap().isEmpty()) {
			myPage.results = computerService.readAll();
			myPage.totalNumberOfRecords = computerService.readTotal();
			myPage.pageNumber = 1;
			request.setAttribute("pageData", myPage);
			return "dashboard";
		}

		int offset = 0;
		int page = 1;
		int limit = 20;
		if (request.getParameter("display") == null
				|| "".equals(request.getParameter("display"))) {
			limit = 20;
		} else {
			System.out.println(request.getParameter("display"));
			if (!request.getParameter("display").matches("\\d+")
					|| Integer.parseInt(request.getParameter("display")) <= 0)
				limit = 20;
			else
				limit = Integer.parseInt(request.getParameter("display"));
		}
		myPage.recordsOnThisPage = limit;

		if (request.getParameter("page") == null
				|| "".equals(request.getParameter("page")))
			page = 1;
		else {
			page = Integer.parseInt(request.getParameter("page"));
			offset = Math.max((page - 1) * limit, 0);
		}
		myPage.pageNumber = page;

		String search = "";
		String sortResult = "c.id";
		if (request.getParameter("search") == null
				|| "".equals(request.getParameter("search"))) {
			search = "";
			myPage.totalNumberOfRecords = computerService.readTotal();
		} else {
			search = request.getParameter("search");
			myPage.totalNumberOfRecords = computerService.readTotal(search);
		}

		String sortMode = "ASC";
		if (request.getParameter("order") == null
				|| "".equals(request.getParameter("order"))) {
			sortMode = "ASC";
		} else {
			sortMode = request.getParameter("order");
		}

		if (request.getParameter("orderField") == null
				|| "".equals(request.getParameter("orderField"))) {
			sortResult = "c.id";
			myPage.resultsOrderedBy = "id";
		} else {
			if ("computer".equals(request.getParameter("orderField"))) {
				sortResult = "c.name";
				myPage.resultsOrderedBy = "computer";
			} else if ("company".equals(request.getParameter("orderField"))) {
				sortResult = "b.name";
				myPage.resultsOrderedBy = "company";
			} else if ("introduced".equals(request.getParameter("orderField"))) {
				sortResult = "c.introduced";
				myPage.resultsOrderedBy = "introduced";
			} else if ("discontinued"
					.equals(request.getParameter("orderField"))) {
				sortResult = "c.discontinued";
				myPage.resultsOrderedBy = "discontinued";
			} else {
				sortResult = "c.id";
				myPage.resultsOrderedBy = "id";
			}
		}

		myPage.orderDirection = sortMode;
		myPage.pageNumber = page;

		request.setAttribute("search", search);
		LOG.debug(sortResult + "/" + sortMode);
		myPage.results = computerService.read(limit, offset, sortResult, sortMode, search);
		
		myPage.numberOfPages = (int) Math.ceil(myPage.totalNumberOfRecords / myPage.recordsOnThisPage) + 1;
		LOG.debug(myPage.toString());
		request.setAttribute("pageData", myPage);
		return "dashboard";
	}
}
