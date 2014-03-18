package com.excilys.formation.projet.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.excilys.formation.projet.dto.ComputerDTO;
import com.excilys.formation.projet.om.domain.Computer;
import com.excilys.formation.projet.service.ComputerService;
import com.excilys.formation.projet.ws.PrintComputersWS;

@WebService(endpointInterface = "com.excilys.formation.projet.ws.PrintComputersWS")
public class PrintComputersWSImpl implements PrintComputersWS{
	ComputerService computerService;
	
	public void setComputerService(ComputerService computerService){
		this.computerService = computerService;
	}
	
	@Override
	public List<ComputerDTO> PrintComputerPage() {
		List<Computer> lC = computerService.read(1, 20, "ASC", "name", "");
		List<ComputerDTO> lCDTO = new ArrayList<ComputerDTO>();
		for(Computer c : lC){
			lCDTO.add(com.excilys.formation.projet.dto.Converter.toDTO(c));
		}
		return lCDTO;
	}

}
