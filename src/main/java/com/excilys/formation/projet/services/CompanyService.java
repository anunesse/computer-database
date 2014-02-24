package com.excilys.formation.projet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.ICompanyDAO;
import com.excilys.formation.projet.dao.impl.CompanyDAO;
import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.servlet.context.ContextGetter;


@Service
public class CompanyService {

	@Autowired
	ICompanyDAO companyDAO;

	/*public CompanyService() {
		ContextGetter.getInstance();
		companyDAO = ContextGetter.getApplicationContext().getBean(CompanyDAO.class);
	}*/
	
	public Company read(long id) {
		DAOFactory.startTransaction();
		Company c = companyDAO.read(id);
		DAOFactory.closeTransaction();
		return c;
	}

	public List<Company> read(int max) {
		DAOFactory.startTransaction();
		List<Company> lc = companyDAO.read(max);
		DAOFactory.closeTransaction();
		return lc;
	}

	public List<Company> read() {
		DAOFactory.startTransaction();
		List<Company> lc = companyDAO.read();
		DAOFactory.closeTransaction();
		return lc;
	}

	public boolean exist(long id) {
		DAOFactory.startTransaction();
		boolean b = companyDAO.exist(id);
		DAOFactory.closeTransaction();
		return b;
	}
}
