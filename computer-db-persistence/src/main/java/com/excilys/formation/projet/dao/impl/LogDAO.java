package com.excilys.formation.projet.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.om.Log;

@Repository
public class LogDAO implements ILogDAO {
	static final Logger LOG = LoggerFactory.getLogger(LogDAO.class);
	
	// @Override
	public List<Log> readAll() {
		//return ((List<Log>)entityManager.createQuery("SELECT log FROM Log as log").getResultList());
		return null;
	}

	// @Override
	public void create(Log log) {
		//entityManager.persist(log);
	}
}