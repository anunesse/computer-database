package com.excilys.formation.projet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.om.domain.Company;
import com.excilys.formation.projet.repositories.CompanyRepository;


@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;
	
	public Company read(long id) {
		return companyRepository.findOne(id);
	}

	public List<Company> read() {
		return (List<Company>) companyRepository.findAll();
	}
}
