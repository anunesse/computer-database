package com.excilys.formation.projet.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.om.Company;
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
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		//SELECT COUNT(*) as long
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		//FROM Computer
		Root<Computer> computer = cq.from(Computer.class);
		//SELECT COUNT(*)
		cq.select(cb.count(computer));
		//LEFT JOIN
		Join<Computer, Company> company = computer.join("company", JoinType.LEFT);
		Query query = entityManager.createQuery(cq);
		return ((Long) query.getSingleResult()).longValue();
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
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
		Root<Computer> computer = cq.from(Computer.class);
		
		Join<Computer, Company> company = computer.join("company", JoinType.LEFT);
		
		search = new StringBuilder("%").append(search).append("%").toString();
		cq.where(cb.or(cb.like(computer.get("name").as(String.class), search), cb.like(company.get("name").as(String.class), search)));
		
		if("DESC".equals(field)){
			if("computer.id".equals(field)){
				cq.orderBy(cb.desc(computer.get("id")));
			}
			else if("computer.name".equals(field)){
				cq.orderBy(cb.desc(computer.get("name")));
			}
			else if("computer.introduced".equals(field)){
				cq.orderBy(cb.desc(computer.get("introduced")));
			}
			else if("computer.discontinued".equals(field)){
				cq.orderBy(cb.desc(computer.get("introduced")));
			}
			else if("company.name".equals(field)){
				cq.orderBy(cb.desc(company.get("name")));
			}
			else{
				cq.orderBy(cb.desc(computer.get("name")));
			}
		}
		else{//ASC
			if("computer.id".equals(field)){
				cq.orderBy(cb.asc(computer.get("id")));
			}
			else if("computer.name".equals(field)){
				cq.orderBy(cb.asc(computer.get("name")));
			}
			else if("computer.introduced".equals(field)){
				cq.orderBy(cb.asc(computer.get("introduced")));
			}
			else if("computer.discontinued".equals(field)){
				cq.orderBy(cb.asc(computer.get("introduced")));
			}
			else if("company.name".equals(field)){
				cq.orderBy(cb.asc(company.get("name")));
			}
			else{
				cq.orderBy(cb.asc(computer.get("name")));
			}
		}
		
		Query query = entityManager.createQuery(cq);
		query.setMaxResults(limit);
		query.setFirstResult(offset);
		return (List<Computer>)query.getResultList();
	}

	/**
	 * Default read function, used to retrieve all data
	 */
	public List<Computer> readAll() {
		//return ((List<Computer>)entityManager.createQuery("SELECT computer FROM Computer as computer LEFT JOIN computer.company company").getResultList());
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		//Creating SELECT Computer
		CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);	
		//Creating Root = FROM
		Root<Computer> computer = cq.from(Computer.class);	
		//Creating LEFT JOIN
		Join<Computer, Company> company = computer.join("company", JoinType.LEFT);
		//Creating ORDER BY
		cq.orderBy(cb.asc(computer.get("name")));
		Query query = entityManager.createQuery(cq);
		return (List<Computer>)query.getResultList();
	}

	/**
	 * Retrieves number of results for research
	 */
	public long readTotal(String search) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Computer> computer = cq.from(Computer.class);
		cq.select(cb.count(computer));
		Join<Computer, Company> company = computer.join("company", JoinType.LEFT);
		search = new StringBuilder("%").append(search).append("%").toString();
		cq.where(cb.or(cb.like(computer.get("name").as(String.class), search), cb.like(company.get("name").as(String.class), search)));
		Query query = entityManager.createQuery(cq);	
		return ((Long)query.getSingleResult()).intValue();
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
