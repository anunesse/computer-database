package com.excilys.formation.projet.services;

import java.sql.Connection;
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
		return myComputerDAO.readTotal();
	}

	public int readTotal(String search) {
		return myComputerDAO.readTotal(search);
	}

	public Computer read(long id) {
		return myComputerDAO.read(id);
	}

	public boolean exist(long id) {
		return myComputerDAO.exist(id);
	}

	public boolean delete(long id) {
		Connection myCon = DAOFactory.getInstance().getConnection();
		boolean b = false;
		try {
			myCon.setAutoCommit(false);
			StringBuilder str = new StringBuilder(
					"Field computer deleted on ID : ");
			str.append(id);
			str.append(";");
			b = myComputerDAO.delete(id, myCon);
			myLogDAO.create(new Log("DELETE", new Date(), str.toString()));
			myCon.commit();
			myCon.setAutoCommit(true);
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		}
		return b;

	}

	public List<Computer> read(int min, int max, String type, String field,
			String search) {
		return myComputerDAO.read(min, max, type, field, search);
	}

	public List<Computer> readAll() {
		return myComputerDAO.readAll();
	}

	public long add(Computer myComp) {
		Connection myCon = DAOFactory.getInstance().getConnection();
		long b = 0;
		try {
			myCon.setAutoCommit(false);
			StringBuilder str = new StringBuilder("Field computer added, ID ");
			b = myComputerDAO.add(myComp, myCon);
			str.append(b);
			str.append(";");
			myLogDAO.create(new Log("CREATE", new Date(), str.toString()));
			myCon.commit();
			myCon.setAutoCommit(true);
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		}
		return b;
	}

	public boolean edit(Computer myComp) {
		// return myComputerDAO.edit(myComp, null);
		Connection myCon = DAOFactory.getInstance().getConnection();
		boolean b = false;
		try {
			myCon.setAutoCommit(false);
			StringBuilder str = new StringBuilder(
					"Field computer edited, ID : ");
			str.append(myComp.getId());
			str.append(";");
			b = myComputerDAO.edit(myComp, myCon);
			myLogDAO.create(new Log("UPDATE", new Date(), str.toString()));
			myCon.commit();
			myCon.setAutoCommit(true);
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		}
		return b;
	}

	public List<Computer> readRangedOrdered(int min, int max, String type,
			String field) {
		return myComputerDAO.readRangedOrdered(min, max, type, field);
	}

	public void CloseConnection(Connection myCon) {
		try {
			myCon.close();
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("[NPEXCEPTION]");
			e.printStackTrace();
		}
	}
}
