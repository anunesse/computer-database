package com.excilys.formation.projet.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.projet.om.Company;

public class CompanyRowMapper implements RowMapper<Company>{

	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Company myComp = new Company();
		myComp.setId(resultSet.getLong(1));
		myComp.setName(resultSet.getString(2));
		return myComp;
	}

}
