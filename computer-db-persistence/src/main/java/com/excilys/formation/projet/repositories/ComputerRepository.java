package com.excilys.formation.projet.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Computer;

@Repository
public interface ComputerRepository extends PagingAndSortingRepository<Computer, Long>{
	//List<Computer> findComputerByNameOrCompanyName(String name, String companyname);
}
