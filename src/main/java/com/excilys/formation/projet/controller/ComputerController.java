package com.excilys.formation.projet.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.formation.projet.controller.wrapper.Page;
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
	protected String getComputer(@Valid Computer computer, BindingResult result, Model model, HttpServletRequest request){
		
		model.addAttribute("options", companyService.read());
		
		if (request.getParameterMap().isEmpty()) {
			model.addAttribute("computer", new Computer());
			return "addComputer";
		} else {
			Computer myComp = computerService.read(Long.parseLong(request.getParameter("computer")));
			if (myComp == null) {
				LOG.error("The computer id could not be found on database.");
				model.addAttribute("answer", 0);
			} else{
				model.addAttribute("computer", myComp);
			}
			return "editComputer";
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
			LOG.info("Computer successfully deleted!");
		} else {
			LOG.info("The computer can not be delete.");
		}
		Page<Computer> myPage = new Page<Computer>();
		myPage.results = computerService.readAll();
		myPage.totalNumberOfRecords = computerService.readTotal();
		model.addAttribute("pageData", myPage);
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
		
		model.addAttribute("options", companyService.read());
		
		if(result.hasErrors()){
			LOG.info("Invalid computer not added.");
			model.addAttribute("computer", computer);
			return "addComputer";
		}
		
		Computer myComputer = null;
		myComputer = computer;

		if (computerService.add(myComputer) > 0) {
			LOG.info("Computer added.");
			Page<Computer> myPage = new Page<Computer>();
			myPage.results = computerService.readAll();
			myPage.totalNumberOfRecords = computerService.readTotal();
			model.addAttribute("pageData", myPage);
			return "dashboard";
		} else {
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
	protected String editComputer(@Valid Computer computer, BindingResult result, Model model, HttpServletRequest request, final RedirectAttributes redirectAttributes){
		model.addAttribute("options", companyService.read());
		//Attention ajouter un redirect pour éviter le F5 utilisateur
		if(result.hasErrors()){
			//model.addAttribute("computer", computer);
			return "editComputer";
		}

		Computer myComputer = null;
		myComputer = computer;
		myComputer.setId(Integer.parseInt(request.getParameter("comp_id")));

		if (computerService.edit(myComputer)){
			LOG.info("Computer successfully edited.");
			Page<Computer> myPage = new Page<Computer>();
			myPage.results = computerService.readAll();
			myPage.totalNumberOfRecords = computerService.readTotal();
			
			redirectAttributes.addFlashAttribute("pageData", myPage);	
			return "redirect:Display";	
		} else {
			LOG.info("Fail to edit computer.");
			return "dashboard";
		}

	}
	

}
