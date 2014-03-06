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
		return companyDAO.read(id);
	}

	public List<Company> read() {
		return companyDAO.read();

	}
}
