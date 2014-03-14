package com.excilys.formation.projet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.om.Log;
import com.excilys.formation.projet.repositories.LogRepository;

	@Service
	public class LogService {
	
	@Autowired
	LogRepository logRepository;
	
	public List<Log> readAll() {
		//return logDAO.readAll();
		return null;
	}
	
	public void create(Log log) {
		//logDAO.create(log);
	}
}