package com.excilys.formation.projet.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.domain.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long>{

}
