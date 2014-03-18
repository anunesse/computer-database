package com.excilys.formation.projet.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.excilys.formation.projet.dto.ComputerDTO;


//@SOAPBinding(style = SOAPBinding.Style.RPC) Wont compile because of List
@WebService
public interface PrintComputersWS {
	
	@WebMethod List<ComputerDTO> PrintComputerPage();

}
