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
import com.excilys.formation.projet.dto.ComputerDTO;
import com.excilys.formation.projet.dto.Converter;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.CompanyService;
import com.excilys.formation.projet.service.ComputerService;


@Controller
public class ComputerController{

	static final Logger LOG = LoggerFactory.getLogger(ComputerController.class);
	
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
		LOG.info("GETTING COMPUTER!");
		model.addAttribute("options", companyService.read());
		
		if (request.getParameterMap().isEmpty()) {
			model.addAttribute("computerDTO", new Computer());
			return "addComputer";
		} else {
			Computer myComp = computerService.read(Long.parseLong(request.getParameter("computer")));
			if (myComp == null) {
				LOG.error("The computer id could not be found on database.");
				model.addAttribute("answer", 0);
			} else{
				model.addAttribute("computerDTO", Converter.toDTO(myComp));
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
		if (computerService.delete(Long.parseLong(request.getParameter("computer_id")))) {
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
	protected String addComputer(@Valid ComputerDTO computerDTO, BindingResult result, Model model, HttpServletRequest request){
		
		model.addAttribute("options", companyService.read());
		
		if(result.hasErrors()){
			LOG.info("Invalid computer not added.");
			//model.addAttribute("computer", Converter.fromDTO(computerDTO));
			return "addComputer";
		}
		
		Computer myComputer = null;
		myComputer = Converter.fromDTO(computerDTO);
		LOG.info("Computer valid going on!");

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
	protected String editComputer(@Valid ComputerDTO computerDTO, BindingResult result, Model model, HttpServletRequest request, final RedirectAttributes redirectAttributes){
		model.addAttribute("options", companyService.read());
		Computer myComputer = null;
		myComputer = Converter.fromDTO(computerDTO);
		myComputer.setId(Integer.parseInt(request.getParameter("comp_id")));
		//Attention ajouter un redirect pour éviter le F5 utilisateur
		if(result.hasErrors()){
			LOG.info("Invalid computer not edited.");
			model.addAttribute("computerDTO", Converter.toDTO(myComputer));
			return "editComputer";
		}

		

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
