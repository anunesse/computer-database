package com.excilys.formation.projet.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.domain.Log;

@Repository
public interface LogRepository extends CrudRepository<Log, Long> {

}
