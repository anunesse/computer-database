package com.excilys.formation.projet.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.ICompanyDAO;
import com.excilys.formation.projet.dao.rowMapper.CompanyRowMapper;
import com.excilys.formation.projet.om.Company;

@Repository
public class CompanyDAO implements ICompanyDAO {
	static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public CompanyDAO() {
		super();
	}

	/**
	 * Retrieve single Company on ID
	 */
	public Company read(long id) {
		String query = "SELECT * FROM company WHERE id = :id";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", id);
		Company company = (Company) namedParameterJdbcTemplate.queryForObject(query, namedParameters, new CompanyRowMapper());
		return company;
	}

	/**
	 * Default companies retriever.
	 */
	public List<Company> read() {
		String query = "SELECT * FROM company";
		return (List<Company>)namedParameterJdbcTemplate.query(query, new CompanyRowMapper());
	}
}
