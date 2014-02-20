package com.excilys.formation.projet.services;

import java.util.List;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.om.Log;

public class LogService {
	ILogDAO myLogDAO;

	public LogService() {
		myLogDAO = DAOFactory.getInstance().getMyLogDAO();
	}

	public List<Log> readAll() {
		return myLogDAO.readAll();
	}

	public boolean create(Log log) {
		return myLogDAO.create(log);
	}
}
