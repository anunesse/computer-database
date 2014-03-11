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
	
	//@Autowired
	//private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	//private EntityManagerFactory entityManagerFactory;
	
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
//		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c LEFT JOIN company b ON c.company_id = b.id WHERE c.id = :id";
//		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		namedParameters.addValue("id", id);
//		Computer computer = (Computer) namedParameterJdbcTemplate.queryForObject(query, namedParameters, new ComputerRowMapper());
//		return computer;
		Computer computer = entityManager.find(Computer.class, id);
		LOG.info("Reading computer with id : " + id);
		return computer;
	}


	/**
	 * delete Computer on ID
	 */
	public void delete(long id) {
//		String query = "DELETE FROM computer WHERE id = :id";
//		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		namedParameters.addValue("id", id);
//		namedParameterJdbcTemplate.update(query, namedParameters);
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
//		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		namedParameters.addValue("name", myComp.getName());
//		if (myComp.getIntroduced() == null){
//			namedParameters.addValue("introduced", null);
//		}
//		else {
//			namedParameters.addValue("introduced", new Timestamp(myComp.getIntroduced().getMillis()));
//		}
//		if (myComp.getDiscontinued() == null){
//			namedParameters.addValue("discontinued", null);
//		}
//		else {
//			namedParameters.addValue("discontinued", new Timestamp(myComp.getDiscontinued().getMillis()));
//		}
//		if(myComp.getCompany().getId()==0){
//			namedParameters.addValue("company_id", null);
//		}
//		else{
//			namedParameters.addValue("company_id", myComp.getCompany().getId());
//		}
//		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(:name, :introduced, :discontinued, :company_id);";
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		namedParameterJdbcTemplate.update(query, namedParameters, keyHolder, new String[]{"id"});
//		return keyHolder.getKey().longValue();
		entityManager.persist(myComp);
		LOG.debug("Persisting a computer in DB : "+myComp.getId());
		return myComp.getId();
	}
	
	/**
	 * Default editor.
	 */
	public void update(Computer myComp) {
//		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		namedParameters.addValue("name", myComp.getName());
//		namedParameters.addValue("id", myComp.getId());
//		if (myComp.getIntroduced() == null){
//			namedParameters.addValue("introduced", null);
//		}
//		else {
//			namedParameters.addValue("introduced", new Timestamp(myComp.getIntroduced().getMillis()));
//		}
//		if (myComp.getDiscontinued() == null){
//			namedParameters.addValue("discontinued", null);
//		}
//		else {
//			namedParameters.addValue("discontinued", new Timestamp(myComp.getDiscontinued().getMillis()));
//		}
//		if(myComp.getCompany().getId()==0){
//			namedParameters.addValue("company_id", null);
//		}
//		else{
//			namedParameters.addValue("company_id", myComp.getCompany().getId());
//		}
//		String query = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id";
//		namedParameterJdbcTemplate.update(query, namedParameters);
		entityManager.merge(myComp);
		LOG.debug("Editing a computer in DB : "+myComp.getId());
	}
}
