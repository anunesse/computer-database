package com.excilys.formation.projet.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.Log;

public class ComputerService {

	static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

	IComputerDAO myComputerDAO;
	ILogDAO myLogDAO;

	public ComputerService() {
		myComputerDAO = DAOFactory.getInstance().getMyComputerDAO();
		myLogDAO = DAOFactory.getInstance().getMyLogDAO();
	}

	public int readTotal() {
		DAOFactory.startTransaction();
		int i = myComputerDAO.readTotal();
		DAOFactory.closeTransaction();
		return i;
	}

	public int readTotal(String search) {
		DAOFactory.startTransaction();
		int i = myComputerDAO.readTotal(search);
		DAOFactory.closeTransaction();
		return i;
	}

	public Computer read(long id) {
		DAOFactory.startTransaction();
		Computer c = myComputerDAO.read(id);
		DAOFactory.closeTransaction();
		return c;
	}

	public boolean exist(long id) {
		DAOFactory.startTransaction();
		boolean b = myComputerDAO.exist(id);
		DAOFactory.closeTransaction();
		return b;
	}

	public boolean delete(long id) {
		// Connection myCon = DAOFactory.getInstance().getConnection();
		boolean b = false;
		try {
			DAOFactory.startTransaction();
			StringBuilder str = new StringBuilder(
					"Field computer deleted on ID : ");
			str.append(id);
			str.append(";");
			b = myComputerDAO.delete(id);
			myLogDAO.create(new Log("DELETE", new Date(), str.toString()));
			if (b)
				DAOFactory.getInstance().getConnection().commit();
			else
				DAOFactory.getInstance().getConnection().rollback();
			DAOFactory.closeTransaction();
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		}
		return b;

	}

	public List<Computer> read(int min, int max, String type, String field,
			String search) {
		DAOFactory.startTransaction();
		List<Computer> lc = myComputerDAO.read(min, max, type, field, search);
		DAOFactory.closeTransaction();
		return lc;
	}

	public List<Computer> readAll() {
		DAOFactory.startTransaction();
		List<Computer> lc = myComputerDAO.readAll();
		DAOFactory.closeTransaction();
		return lc;
	}

	public long add(Computer myComp) {

		long b = 0;
		try {
			DAOFactory.startTransaction();
			StringBuilder str = new StringBuilder("Field computer added, ID ");
			b = myComputerDAO.add(myComp);
			str.append(b);
			str.append(";");
			myLogDAO.create(new Log("CREATE", new Date(), str.toString()));
			if (b > 0)
				DAOFactory.getInstance().getConnection().commit();
			else
				DAOFactory.getInstance().getConnection().rollback();
			DAOFactory.closeTransaction();
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		}
		return b;
	}

	public boolean edit(Computer myComp) {
		DAOFactory.startTransaction();
		boolean b = false;
		try {
			StringBuilder str = new StringBuilder(
					"Field computer edited, ID : ");
			str.append(myComp.getId());
			str.append(";");
			b = myComputerDAO.edit(myComp);
			myLogDAO.create(new Log("UPDATE", new Date(), str.toString()));
			if (b)
				DAOFactory.getInstance().getConnection().commit();
			else
				DAOFactory.getInstance().getConnection().rollback();
			DAOFactory.closeTransaction();
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		}
		return b;
	}

	public List<Computer> readRangedOrdered(int min, int max, String type,
			String field) {
		DAOFactory.startTransaction();
		List<Computer> lc = myComputerDAO.readRangedOrdered(min, max, type,
				field);
		DAOFactory.closeTransaction();
		return lc;
	}
}
