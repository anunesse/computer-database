package com.excilys.formation.projet.services;

import java.util.List;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.ICompanyDAO;
import com.excilys.formation.projet.om.Company;

public class CompanyService {
	ICompanyDAO myCompanyDAO;

	public CompanyService() {
		myCompanyDAO = DAOFactory.getInstance().getMyCompanyDAO();
	}

	public Company read(long id) {
		DAOFactory.startTransaction();
		Company c = myCompanyDAO.read(id);
		DAOFactory.closeTransaction();
		return c;
	}

	public List<Company> read(int max) {
		DAOFactory.startTransaction();
		List<Company> lc = myCompanyDAO.read(max);
		DAOFactory.closeTransaction();
		return lc;
	}

	public List<Company> read() {
		DAOFactory.startTransaction();
		List<Company> lc = myCompanyDAO.read();
		DAOFactory.closeTransaction();
		return lc;
	}

	public boolean exist(long id) {
		DAOFactory.startTransaction();
		boolean b = myCompanyDAO.exist(id);
		DAOFactory.closeTransaction();
		return b;
	}
}
