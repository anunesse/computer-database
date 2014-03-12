package com.excilys.formation.projet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.dao.Ordered;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.QComputer;
import com.mysema.query.jpa.impl.JPAQuery;


@Repository
public class ComputerDAO implements IComputerDAO {
	static final Logger LOG = LoggerFactory.getLogger(ComputerDAO.class);


	//JPA Hibernate : https://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html/configuration.html
	@PersistenceContext(unitName="entityManagerFactory")
	private EntityManager entityManager;

	public ComputerDAO() {
	super();
	}

	/**
	* Return COUNT(*)
	*/
	public long readTotal() {
		JPAQuery query = new JPAQuery(entityManager);
		QComputer computer = QComputer.computer;
		return query.from(computer).leftJoin(computer.company).count();
	}

	/**
	* Read single computer on ID
	*/
	public Computer read(long id) {
		Computer computer = entityManager.find(Computer.class, id);
		LOG.info("Reading computer with id : " + id);
		return computer;
	}


	/**
	* Delete Computer on ID
	*/
	public void delete(long id) {
		Computer computer = entityManager.find(Computer.class, id);
		entityManager.remove(computer);
		LOG.info("Deleting computer from database.");
	}

	/**
	* Default read function.
	*
	* @param limit
	* @param offset
	* @param type
	* @param field
	* @param search
	* @return
	*/
	public List<Computer> read(int limit, int offset, String type, String field, String search) {	
		JPAQuery query = new JPAQuery(entityManager);
		
		QComputer computer = QComputer.computer;
		query = query.from(computer).leftJoin(computer.company);
		if(!"".equals(search)){
			search = new StringBuilder("%").append(search).append("%").toString();
			query = query.where(computer.name.like(search).or(computer.company.name.like(search)));
		}
		Ordered myOrder = new Ordered(field, type);
		LOG.error("_______________________________________________________"+type+"/"+field);
		query = query.orderBy(myOrder.getMySpecifier());
		query = query.limit(limit);
		query = query.offset(offset);
		return query.list(computer);
	}

	/**
	* Default read function, used to retrieve all data
	*/
	public List<Computer> readAll() {
		JPAQuery query = new JPAQuery(entityManager);
		QComputer computer = QComputer.computer;
		return query.from(computer).leftJoin(computer.company).list(computer);
	}

	/**
	* Retrieves number of results for research
	*/
	public long readTotal(String search) {
		JPAQuery query = new JPAQuery(entityManager);
		QComputer computer = QComputer.computer;
		query = query.from(computer).leftJoin(computer.company);
		if(!"".equals(search)){
			search = new StringBuilder("%").append(search).append("%").toString();
			query = query.where(computer.name.like(search).or(computer.company.name.like(search)));
		}
		return query.count();
	}

	public long create(Computer myComp) {
		entityManager.persist(myComp);
		LOG.debug("Persisting a computer in DB : "+myComp.getId());
		return myComp.getId();
	}

	/**
	* Default editor.
	*/
	public void update(Computer myComp) {
		entityManager.merge(myComp);
		LOG.debug("Editing a computer in DB : "+myComp.getId());
	}
}
