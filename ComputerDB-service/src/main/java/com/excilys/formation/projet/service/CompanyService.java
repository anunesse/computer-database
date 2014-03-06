package com.excilys.formation.projet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.dao.ICompanyDAO;
import com.excilys.formation.projet.om.Company;


@Service
public class CompanyService {

	@Autowired
	ICompanyDAO companyDAO;
	
	public Company read(long id) {
		Company c = companyDAO.read(id);
		return c;
	}

	public List<Company> read(int max) {
		//DAOFactory.startTransaction();
		List<Company> lc = companyDAO.read(max);
		//DAOFactory.closeTransaction();
		return lc;
	}

	public List<Company> read() {
		List<Company> lc = companyDAO.read();
		return lc;
	}

	public boolean exist(long id) {
		return companyDAO.exist(id);
	}
}
