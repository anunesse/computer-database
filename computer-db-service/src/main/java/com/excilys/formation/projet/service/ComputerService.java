package com.excilys.formation.projet.service;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.Log;

@Service
@Transactional
public class ComputerService {

	static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

	@Autowired
	IComputerDAO computerDAO;
	
	@Autowired
	ILogDAO logDAO;
	
	public ComputerService(){
		super();
	}
	
	@Transactional(readOnly=true)
	public long readTotal(String search) {
		return computerDAO.readTotal(search);
	}

	@Transactional(readOnly=true)
	public Computer read(long id) {
		return computerDAO.read(id);
	}

	public void delete(long id) {
		boolean b = false;
		StringBuilder str = new StringBuilder(
				"Field computer deleted on ID : ");
		str.append(id);
		str.append(";");
		computerDAO.delete(id);
		logDAO.create(new Log("DELETE", new DateTime(), str.toString()));
	}

	@Transactional(readOnly=true)
	public List<Computer> read(int min, int max, String type, String field,
			String search) {
		List<Computer> lc = computerDAO.read(min, max, type, field, search);
		return lc;
	}

	public long create(Computer myComp) {
		long b = 0;
		StringBuilder str = new StringBuilder("Field computer added, ID ");
		b = computerDAO.create(myComp);
		str.append(b);
		str.append(";");
		logDAO.create(new Log("CREATE", new DateTime(), str.toString()));
		return b;
	}

	public void update(Computer myComp) {
		boolean b = false;
		StringBuilder str = new StringBuilder(
				"Field computer edited, ID : ");
		str.append(myComp.getId());
		str.append(";");
		computerDAO.update(myComp);
		logDAO.create(new Log("UPDATE", new DateTime(), str.toString()));
	}
}
