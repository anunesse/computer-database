package com.excilys.formation.projet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.om.Log;

	@Service
	public class LogService {
	
	@Autowired
	ILogDAO logDAO;
	
	public List<Log> readAll() {
		return logDAO.readAll();
	}
	
	public boolean create(Log log) {
		return logDAO.create(log);
	}
}