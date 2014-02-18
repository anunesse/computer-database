package com.excilys.formation.projet.controller;

import java.util.List;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.om.Computer;

public class ComputerController {
	IComputerDAO myComputerDAO = DAOFactory.getInstance().getMyComputerDAO();

	public ComputerController() {
		super();
	}

	public int readTotal() {
		return myComputerDAO.readTotal();
	}

	public int readTotal(String search) {
		return myComputerDAO.readTotal(search);
	}

	public Computer read(long id) {
		return myComputerDAO.read(id);
	}

	public boolean exist(long id) {
		return myComputerDAO.exist(id);
	}

	public boolean delete(long id) {
		return myComputerDAO.delete(id);
	}

	public List<Computer> read(int min, int max, String type, String field,
			String search) {
		return myComputerDAO.read(min, max, type, field, search);
	}

	public List<Computer> readAll() {
		return myComputerDAO.readAll();
	}

	public boolean add(Computer myComp) {
		return myComputerDAO.add(myComp);
	}

	public boolean edit(Computer myComp) {
		return myComputerDAO.edit(myComp);
	}

	public List<Computer> readRangedOrdered(int min, int max, String type,
			String field) {
		return myComputerDAO.readRangedOrdered(min, max, type, field);
	}

}
