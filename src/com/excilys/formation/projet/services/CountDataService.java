package com.excilys.formation.projet.services;

import com.excilys.formation.projet.dao.CompanyDAO;
import com.excilys.formation.projet.dao.ComputerDAO;
import com.excilys.formation.projet.dao.DAOFactory;

public class CountDataService {	
	public static int CountComputers(){
ComputerDAO myComputerDAO = DAOFactory.getInstance().getMyComputerDAO();
		//myComputerDAO.
		return 580;
	}
	public static int CountCompanys(){
		return 43;
	}
}
