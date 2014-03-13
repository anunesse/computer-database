package com.excilys.formation.projet.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.ICompanyDAO;
import com.excilys.formation.projet.om.Company;

@Repository
public class CompanyDAO implements ICompanyDAO {
	static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);
	
	public CompanyDAO() {
		super();
	}

	/**
	 * Retrieve single Company on ID
	 */
	public Company read(long id) {
		//LOG.info("Reading company with id : " + id);
		//return entityManager.find(Company.class, id);
		return null;
	}

	/**
	 * Default companies retriever.
	 */
	public List<Company> read() {
		//return ((List<Company>)entityManager.createQuery("SELECT company FROM Company as company").getResultList());
		return null;
	}
}
