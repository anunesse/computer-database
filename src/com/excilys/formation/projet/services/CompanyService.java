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
		return myCompanyDAO.read(id);
	}

	public List<Company> read(int max) {
		return myCompanyDAO.read(max);
	}

	public List<Company> read() {
		return myCompanyDAO.read();
	}

	public boolean exist(long id) {
		return myCompanyDAO.exist(id);
	}
}
