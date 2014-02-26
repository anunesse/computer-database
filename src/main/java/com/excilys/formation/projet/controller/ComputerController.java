package com.excilys.formation.projet.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.projet.controller.wrapper.Page;
import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.services.CompanyService;
import com.excilys.formation.projet.services.ComputerService;


@Controller
public class ComputerController{

	static final Logger LOG = LoggerFactory
			.getLogger(ComputerController.class);
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;

	/**
	 * [GET] GET computer.
	 * RequestMapping(value="GetComputer", method = RequestMethod.GET).
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="GetComputer", method = RequestMethod.GET)
	protected String getComputer(Model model, HttpServletRequest request){
		request.setAttribute("options", companyService.read());
		if (request.getParameterMap().isEmpty()) {
			LOG.error("The computer id could not be found on request.");
			request.setAttribute("answer", 0);
			return "addComputer";
		} else {
			Computer myComp = computerService.read(Long.parseLong(request
					.getParameter("computer")));
			if (myComp == null) {
				LOG.error("The computer id could not be found on database.");
				request.setAttribute("answer", 0);
			} else
				request.setAttribute("computer", myComp);
			return "addComputer";
		}
	}
	
	/**
	 * [GET] DELETE computer.
	 * RequestMapping(value="DelComputer", method = RequestMethod.GET).
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="DelComputer", method = RequestMethod.GET)
	protected String delComputer(Model model, HttpServletRequest request){
		if (computerService.delete(Long.parseLong(request
				.getParameter("computer")))) {
			request.setAttribute(
					"error",
					new com.excilys.formation.projet.controller.wrapper.Error(
							"success", "Computer deleted",
							"The computer was successfully deleted from the database."));
			LOG.info("Computer successfully deleted!");
		} else {
			request.setAttribute(
					"error",
					new com.excilys.formation.projet.controller.wrapper.Error(
							"danger", "Fail to delete computer",
							"The computer was not deleted to the database please try again."));
			LOG.info("The computer can not be delete.");
		}
		Page<Computer> myPage = new Page<Computer>();
		myPage.results = computerService.readAll();
		myPage.totalNumberOfRecords = computerService.readTotal();
		request.setAttribute("pageData", myPage);
		return "dashboard";
	}
	
	/**
	 * [POST] ADD computer
	 * RequestMapping(value="AddComputer", method = RequestMethod.POST).
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="AddComputer", method = RequestMethod.POST)
	protected String addComputer(@Valid Computer computer, BindingResult result, Model model, HttpServletRequest request){
		//Attention ajouter un redirect pour éviter le F5 utilisateur
		if(result.hasErrors()){
			request.setAttribute("error",
					new com.excilys.formation.projet.controller.wrapper.Error(
							"danger", "Fail to add computer",
							"The computer was not added to database, please try again."));
			request.setAttribute("mode", request.getParameter("mode"));
			return "addComputer";
		}
		Computer myComp = null;
		if (companyService.exist(Long.parseLong(request
				.getParameter("company")))) {
			DateFormat formatter;
			Date dateFormattedIntroduced = null;
			Date dateFormattedDiscontinued = null;
			formatter = new SimpleDateFormat("yyyy-mm-dd");
			try {
				dateFormattedIntroduced = (Date) formatter.parse(request
						.getParameter("introduced"));
				dateFormattedDiscontinued = (Date) formatter.parse(request
						.getParameter("discontinued"));
			} catch (ParseException e1) {
				LOG.error("Adding computer parse exception catch.");
				e1.printStackTrace();
			}
			myComp = new Computer(request.getParameter("name"),
					dateFormattedIntroduced, dateFormattedDiscontinued,
					new Company(Long.parseLong(request
							.getParameter("company"))));

		}

		if (computerService.add(myComp) > 0) {
			request.setAttribute(
					"error",
					new com.excilys.formation.projet.controller.wrapper.Error(
							"success", "Computer added",
							"Congratulations, the computer has been added to the database."));
			Page<Computer> myPage = new Page<Computer>();
			myPage.results = computerService.readAll();
			myPage.totalNumberOfRecords = computerService.readTotal();
			request.setAttribute("pageData", myPage);
			/*request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(
					request, response);*/
			return "dashboard";
		} else {
			request.setAttribute(
					"error",
					new com.excilys.formation.projet.controller.wrapper.Error(
							"danger", "Fail to add computer",
							"The computer was not added to the database please try again."));
			/*request.getRequestDispatcher("WEB-INF/addComputer.jsp")
					.forward(request, response);*/
			LOG.info("The computer can not be add.");
			return "addComputer";
		}
	}

	/**
	 * [POST] EDIT computer.
	 * RequestMapping(value="EditComputer", method = RequestMethod.POST).
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="EditComputer", method = RequestMethod.POST)
	protected String editComputer(@Valid Computer computer, BindingResult result, Model model, HttpServletRequest request){
		//Attention ajouter un redirect pour éviter le F5 utilisateur
		if(result.hasErrors()){
			request.setAttribute("error",
					new com.excilys.formation.projet.controller.wrapper.Error(
							"danger", "Fail to edit computer",
							"The computer was not edited please try again. [UNBIND]"));
			LOG.info("Fail to access edit computer.");
			request.setAttribute("computer", request.getParameter("comp_id"));
			/*request.getRequestDispatcher(
					"WEB-INF/addComputer.jsp?computer="
							+ request.getParameter("comp_id")).forward(
					request, response);
			return;*/
			return "addComputer";
		}

		Computer myComp = null;

		DateFormat formatter;
		Date dateFormattedIntroduced = null;
		Date dateFormattedDiscontinued = null;
		formatter = new SimpleDateFormat("yyyy-mm-dd");
		if (!"".equals(request.getParameter("introduced"))) {
			try {
				dateFormattedIntroduced = (Date) formatter.parse(request
						.getParameter("introduced"));
			} catch (ParseException e1) {
				LOG.error("Introduced date parse exception catch.");
				e1.printStackTrace();
				return "addComputer";
			}
		}
		if (!"".equals(request.getParameter("discontinued"))) {
			try {
				dateFormattedDiscontinued = (Date) formatter.parse(request
						.getParameter("discontinued"));
			} catch (ParseException e1) {
				LOG.error("Discontinued date parse exception catch.");
				e1.printStackTrace();
				return "addComputer";
			}
		}

		myComp = new Computer(Long.parseLong(request
				.getParameter("comp_id")), request.getParameter("name"),
				dateFormattedIntroduced, dateFormattedDiscontinued,
				companyService.read(Long.parseLong(request
						.getParameter("company"))));

		if (computerService.edit(myComp)) {
			request.setAttribute(
					"error",
					new com.excilys.formation.projet.controller.wrapper.Error(
							"success", "Computer edited",
							"Congratulations, the computer has been edited and data overwritten."));
			LOG.info("Computer successfully edited.");
			Page<Computer> myPage = new Page<Computer>();
			myPage.results = computerService.readAll();
			myPage.totalNumberOfRecords = computerService.readTotal();
			request.setAttribute("pageData", myPage);
			return "dashboard";
		} else {
			request.setAttribute(
					"error",
					new com.excilys.formation.projet.controller.wrapper.Error(
							"danger", "Fail to edit computer",
							"The computer was not updated, please try again."));
			LOG.info("Fail to edit computer.");
			/*request.getRequestDispatcher(
					"WEB-INF/addComputer.jsp?computer="
							+ request.getParameter("comp_id")).forward(
					request, response);*/
			return "dashboard";
		}

	}
	

}
