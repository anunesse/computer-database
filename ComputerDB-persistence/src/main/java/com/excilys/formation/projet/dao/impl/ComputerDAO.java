package com.excilys.formation.projet.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.dao.rowMapper.ComputerRowMapper;
import com.excilys.formation.projet.om.Computer;

@Repository
public class ComputerDAO implements IComputerDAO {
	static final Logger LOG = LoggerFactory.getLogger(ComputerDAO.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public ComputerDAO() {
		super();
	}

	/**
	 * Return COUNT(*)
	 */
	public int readTotal() {
		String query = "SELECT COUNT(*) FROM computer c LEFT JOIN company b ON c.company_id = b.id";
		return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource(), Integer.class);
	}

	/**
	 * Read single computer on ID
	 */
	public Computer read(long id) {
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c LEFT JOIN company b ON c.company_id = b.id WHERE c.id = :id";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		Computer computer = (Computer) namedParameterJdbcTemplate.queryForObject(query, namedParameters, new ComputerRowMapper());
		return computer;
	}


	/**
	 * delete Computer on ID
	 */
	public void delete(long id) {
		String query = "DELETE FROM computer WHERE id = :id";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		namedParameterJdbcTemplate.update(query, namedParameters);
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
	public List<Computer> read(int limit, int offset, String type,
			String field, String search) {
		StringBuilder query = new StringBuilder("SELECT c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c LEFT JOIN company b ON c.company_id = b.id");
		query.append(" WHERE c.name LIKE :search OR b.name LIKE :search ORDER BY ");
		query.append(type).append(" ").append(field);
		query.append(" LIMIT :limit OFFSET :offset");
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("search", "%"+search+"%");
		namedParameters.addValue("limit", limit);
		namedParameters.addValue("offset", offset);
		return namedParameterJdbcTemplate.query(query.toString(), namedParameters, new ComputerRowMapper());
	}

	/**
	 * Default read function, used to retrieve all data
	 */
	public List<Computer> readAll() {
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c LEFT JOIN company b ON c.company_id = b.id ORDER BY c.name";
		return (List<Computer>)namedParameterJdbcTemplate.query(query, new ComputerRowMapper());
	}

	/**
	 * Retrieves number of results for research
	 */
	public int readTotal(String search) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("search", "%"+search+"%");
		String query = "SELECT COUNT(*) FROM computer c LEFT JOIN company b ON c.company_id = b.id WHERE c.name LIKE :search OR b.name LIKE :search";
		return namedParameterJdbcTemplate.queryForObject(query, namedParameters, Integer.class);
	}

	public long create(Computer myComp) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("name", myComp.getName());
		if (myComp.getIntroduced() == null){
			namedParameters.addValue("introduced", null);
		}
		else {
			namedParameters.addValue("introduced", new Timestamp(myComp.getIntroduced().getMillis()));
		}
		if (myComp.getDiscontinued() == null){
			namedParameters.addValue("discontinued", null);
		}
		else {
			namedParameters.addValue("discontinued", new Timestamp(myComp.getDiscontinued().getMillis()));
		}
		if(myComp.getCompany().getId()==0){
			namedParameters.addValue("company_id", null);
		}
		else{
			namedParameters.addValue("company_id", myComp.getCompany().getId());
		}
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(:name, :introduced, :discontinued, :company_id);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(query, namedParameters, keyHolder, new String[]{"id"});
		return keyHolder.getKey().longValue();
	}
	
	/**
	 * Default editor.
	 */
	public void update(Computer myComp) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("name", myComp.getName());
		namedParameters.addValue("id", myComp.getId());
		if (myComp.getIntroduced() == null){
			namedParameters.addValue("introduced", null);
		}
		else {
			namedParameters.addValue("introduced", new Timestamp(myComp.getIntroduced().getMillis()));
		}
		if (myComp.getDiscontinued() == null){
			namedParameters.addValue("discontinued", null);
		}
		else {
			namedParameters.addValue("discontinued", new Timestamp(myComp.getDiscontinued().getMillis()));
		}
		if(myComp.getCompany().getId()==0){
			namedParameters.addValue("company_id", null);
		}
		else{
			namedParameters.addValue("company_id", myComp.getCompany().getId());
		}
		String query = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id";
		namedParameterJdbcTemplate.update(query, namedParameters);
	}
}
