package com.excilys.formation.projet.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	protected String getComputer(Model model, @RequestParam(value = "computer", required = false) String Id){
		LOG.info("GETTING COMPUTER!");
		model.addAttribute("options", companyService.read());
		
		if (Id == null || "".equals(Id)) {
			model.addAttribute("computerDTO", new Computer());
			return "addComputer";
		} else {
			Computer myComp = computerService.read(Long.parseLong(Id));
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
	 * @param Id
	 * @return
	 */
	@RequestMapping(value="DelComputer", method = RequestMethod.GET)
	protected String delComputer(Model model, @RequestParam(value = "computer_id", required = true) String Id){
		computerService.delete(Long.parseLong(Id));
		Page<Computer> myPage = new Page<Computer>();
		myPage.results = computerService.read(20, 0, "computer.name", "ASC", "");
		myPage.totalNumberOfRecords = computerService.readTotal("");
		myPage.setPageNumber(1);
		myPage.setRecordsOnThisPage(20);
		myPage.setNumberOfPages((int) Math.ceil(myPage.totalNumberOfRecords / myPage.recordsOnThisPage) + 1);
		model.addAttribute("pageData", myPage);
		return "dashboard";
	}
	
	/**
	 * [POST] ADD computer
	 * RequestMapping(value="AddComputer", method = RequestMethod.POST).
	 * @param model
	 * @param computerDTO
	 * @param result
	 * @return
	 */
	@RequestMapping(value="AddComputer", method = RequestMethod.POST)
	protected String addComputer(@Valid ComputerDTO computerDTO, BindingResult result, Model model){
		if(result.hasErrors()){
			model.addAttribute("options", companyService.read());
			LOG.info("Invalid computer not added.");
			return "addComputer";
		}
		
		Computer myComputer = null;
		myComputer = Converter.fromDTO(computerDTO);
		LOG.info("Computer valid going on!");

		if (computerService.create(myComputer) > 0) {
			LOG.info("Computer added.");
			Page<Computer> myPage = new Page<Computer>();
			
			myPage.results = computerService.read(20, 0, "computer.name", "ASC", "");
			myPage.totalNumberOfRecords = computerService.readTotal("");
			myPage.setPageNumber(1);
			myPage.setRecordsOnThisPage(20);
			myPage.setNumberOfPages((int) Math.ceil(myPage.totalNumberOfRecords / myPage.recordsOnThisPage) + 1);
			model.addAttribute("pageData", myPage);
			return "dashboard";
			
		} else {
			model.addAttribute("options", companyService.read());
			LOG.info("The computer can not be add.");
			return "addComputer";
		}
	}

	/**
	 * [POST] EDIT computer.
	 * RequestMapping(value="EditComputer", method = RequestMethod.POST).
	 * @param model
	 * @param computerDTO
	 * @param result
	 * @return
	 */
	@RequestMapping(value="EditComputer", method = RequestMethod.POST)
	protected String editComputer(@Valid ComputerDTO computerDTO, BindingResult result, Model model, final RedirectAttributes redirectAttributes){
		if(result.hasErrors()){
			model.addAttribute("options", companyService.read());
			LOG.info("Invalid computer not edited.");
			return "editComputer";
		}
		Computer myComputer = new Computer();
		myComputer = Converter.fromDTO(computerDTO);
		computerService.update(myComputer);
		LOG.info("Computer successfully edited.");
		Page<Computer> myPage = new Page<Computer>();
		
		myPage.results = computerService.read(20, 0, "computer.name", "ASC", "");
		myPage.totalNumberOfRecords = computerService.readTotal("");
		myPage.setPageNumber(1);
		myPage.setRecordsOnThisPage(20);
		myPage.setNumberOfPages((int) Math.ceil(myPage.totalNumberOfRecords / myPage.recordsOnThisPage) + 1);
		model.addAttribute("PageData", myPage);
		
		return "dashboard";
	}
}
