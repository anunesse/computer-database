package com.excilys.formation.projet.services;

import com.excilys.formation.projet.DAO.CompanyDAO;
import com.excilys.formation.projet.DAO.ComputerDAO;
import com.excilys.formation.projet.DAO.DAOFactory;

public class CountDataService {
	private static ComputerDAO myComputerDAO;
	private static CompanyDAO myCompanyDAO;
	public static int CountComputers(){
		DAOFactory.getInstance();
		myComputerDAO = DAOFactory.getMyComputerDAO();
		return myComputerDAO.getTotalComputer();
	}
	public static int CountCompanys(){
		return 43;
	}
}
