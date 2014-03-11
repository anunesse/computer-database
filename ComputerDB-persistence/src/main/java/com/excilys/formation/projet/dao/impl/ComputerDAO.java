package com.excilys.formation.projet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.om.Computer;

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
	public Long readTotal() {
		return ((Long)entityManager.createQuery("SELECT COUNT(computer) FROM Computer as computer LEFT JOIN computer.company company").getSingleResult());
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
	 * delete Computer on ID
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
		String order = "ORDER BY "+type+" "+field;
		return entityManager.createQuery("SELECT computer FROM Computer as computer LEFT JOIN computer.company company WITH company.name LIKE :search WHERE computer.name LIKE :search " + order)
				.setParameter("search", "%" + search + "%")
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	/**
	 * Default read function, used to retrieve all data
	 */
	public List<Computer> readAll() {
		return ((List<Computer>)entityManager.createQuery("SELECT computer FROM Computer as computer LEFT JOIN computer.company company").getResultList());
	}

	/**
	 * Retrieves number of results for research
	 */
	public long readTotal(String search) {
		return (Long)entityManager.createQuery("SELECT COUNT(computer) FROM Computer as computer LEFT JOIN computer.company company WITH company.name LIKE :search WHERE computer.name LIKE :search")
				.setParameter("search", "%" + search + "%")
				.getSingleResult();
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
