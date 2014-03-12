package com.excilys.formation.projet.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.Log;

@Repository
public class ComputerDAO implements IComputerDAO {
	static final Logger LOG = LoggerFactory.getLogger(ComputerDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public ComputerDAO() {
		super();
	}

	/**
	 * Read single computer on ID
	 */
	public Computer read(long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Computer computer = (Computer)session.get(Computer.class, id);
		session.getTransaction().commit();
		return computer;
	}


	/**
	 * delete Computer on ID
	 */
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Computer computer = (Computer)session.get(Computer.class, id);
		session.delete(computer);
		session.save(new Log("DELETE",new DateTime(), "Deleting a computer from DB : "+id));
		LOG.debug("Deleting a computer from DB : "+id);
		session.getTransaction().commit();
	}

	
	/**
	 * Retrieves number of results for research
	 */
	public long readTotal(String search) {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Computer.class)
				.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.like("name", "%"+search+"%"))
				.add(Restrictions.like("company.name", "%"+search+"%"))
				.list().size();
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
		Session session = sessionFactory.getCurrentSession();
		List<Computer> computers = session.createCriteria(Computer.class)
					.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.or(Restrictions.like("name", "%"+search+"%"), Restrictions.like("company.name", "%"+search+"%")))
					.setMaxResults(limit)
					.setFirstResult(offset).list();
		return computers;
	}

	/**
	 * Return COUNT(*)
	 */
	public long readTotal() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Computer.class).createAlias("company", "company", JoinType.LEFT_OUTER_JOIN).list().size();
	}
	
	/**
	 * Default read function, used to retrieve all data
	 */
	@SuppressWarnings("unchecked")
	public List<Computer> readAll() {
		Session session = sessionFactory.getCurrentSession();
		return (List<Computer>)session.createCriteria(Computer.class).createAlias("company", "company", JoinType.LEFT_OUTER_JOIN).list();
	}

	public long create(Computer myComp) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(myComp);
		session.save(new Log("CREATE",new DateTime(), "Computer created on database id : "+myComp.getId()));
		LOG.debug("Creating a computer in DB : "+myComp.getId());
		session.getTransaction().commit();
		return myComp.getId();
	}
	
	/**
	 * Default editor.
	 */
	public void update(Computer myComp) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.update(myComp);
		session.save(new Log("UPDATE",new DateTime(), "Computer updated in database id : "+myComp.getId()));
		LOG.debug("Updating a computer in DB : "+myComp.getId());
		session.getTransaction().commit();
	}
}
