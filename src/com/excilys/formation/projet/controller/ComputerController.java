package com.excilys.formation.projet.controller;

import java.util.List;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.om.Computer;

public class ComputerController {
	private List<Computer> myComputers;
	IComputerDAO myComputerDAO = DAOFactory.getInstance().getMyComputerDAO();
	
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
