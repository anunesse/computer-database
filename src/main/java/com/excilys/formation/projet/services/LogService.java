package com.excilys.formation.projet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.dao.impl.LogDAO;
import com.excilys.formation.projet.om.Log;
import com.excilys.formation.projet.servlet.context.ContextGetter;

@Service
public class LogService {
	
	@Autowired
	ILogDAO logDAO;

	/*public LogService() {
		ContextGetter.getInstance();
		logDAO = ContextGetter.getApplicationContext().getBean(LogDAO.class);
	}*/

	public List<Log> readAll() {
		return logDAO.readAll();
	}

	public boolean create(Log log) {
		return logDAO.create(log);
	}
}
