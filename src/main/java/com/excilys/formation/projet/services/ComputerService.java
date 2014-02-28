package com.excilys.formation.projet.services;

import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.Log;

@Service
public class ComputerService {

	static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

	@Autowired
	IComputerDAO computerDAO;
	
	@Autowired
	ILogDAO logDAO;
	
	public ComputerService(){
		super();
		System.out.println("SPRING SETTING COMPUTER SERVICE_____________!");
	}

	public int readTotal() {
		DAOFactory.startTransaction();
		int i = computerDAO.readTotal();
		DAOFactory.closeTransaction();
		return i;
	}

	public int readTotal(String search) {
		DAOFactory.startTransaction();
		int i = computerDAO.readTotal(search);
		DAOFactory.closeTransaction();
		return i;
	}

	public Computer read(long id) {
		DAOFactory.startTransaction();
		Computer c = computerDAO.read(id);
		DAOFactory.closeTransaction();
		return c;
	}

	public boolean exist(long id) {
		DAOFactory.startTransaction();
		boolean b = computerDAO.exist(id);
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
			b = computerDAO.delete(id);
			logDAO.create(new Log("DELETE", new DateTime(), str.toString()));
			if (b)
				DAOFactory.getConnection().commit();
			else
				DAOFactory.getConnection().rollback();
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
		List<Computer> lc = computerDAO.read(min, max, type, field, search);
		DAOFactory.closeTransaction();
		return lc;
	}

	public List<Computer> readAll() {
		DAOFactory.startTransaction();
		List<Computer> lc = computerDAO.readAll();
		DAOFactory.closeTransaction();
		return lc;
	}

	public long add(Computer myComp) {

		long b = 0;
		try {
			DAOFactory.startTransaction();
			StringBuilder str = new StringBuilder("Field computer added, ID ");
			b = computerDAO.add(myComp);
			str.append(b);
			str.append(";");
			logDAO.create(new Log("CREATE", new DateTime(), str.toString()));
			if (b > 0)
				DAOFactory.getConnection().commit();
			else
				DAOFactory.getConnection().rollback();
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
			b = computerDAO.edit(myComp);
			logDAO.create(new Log("UPDATE", new DateTime(), str.toString()));
			if (b)
				DAOFactory.getConnection().commit();
			else
				DAOFactory.getConnection().rollback();
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
		List<Computer> lc = computerDAO.readRangedOrdered(min, max, type,
				field);
		DAOFactory.closeTransaction();
		return lc;
	}
}
