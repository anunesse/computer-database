package com.excilys.formation.projet.services;

import com.excilys.formation.projet.DAO.CompanyDAO;
import com.excilys.formation.projet.DAO.ComputerDAO;
import com.excilys.formation.projet.DAO.DAOFactory;

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
