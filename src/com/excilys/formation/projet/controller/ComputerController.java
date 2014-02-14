package com.excilys.formation.projet.controller;

import java.util.List;

import com.excilys.formation.projet.DAO.CompanyDAO;
import com.excilys.formation.projet.DAO.ComputerDAO;
import com.excilys.formation.projet.DAO.DAOFactory;
import com.excilys.formation.projet.OM.Computer;

public class ComputerController {
	private List<Computer> myComputers;
	ComputerDAO myComputerDAO = DAOFactory.getInstance().getMyComputerDAO();
	
	public ComputerController() {
		super();
	}
	
	public void searchByName(List<Computer> list, CharSequence CS){
		for(Computer myComp : list){
			if(!myComp.getName().contains(CS))
				list.remove(myComp);
		}
	}
	
	public void searchByCompany(List<Computer> list, CharSequence CS){
		for(Computer myComp : list){
			if(!myComp.getCompany().getName().contains(CS))
				list.remove(myComp);
		}
	}
	
}
