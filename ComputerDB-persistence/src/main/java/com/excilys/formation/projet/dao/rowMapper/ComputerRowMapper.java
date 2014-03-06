package com.excilys.formation.projet.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;

public class ComputerRowMapper implements RowMapper<Computer>{

	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Computer myComp = new Computer();
		myComp.setId(resultSet.getLong(1));
		myComp.setName(resultSet.getString(2));
		if(resultSet.getTimestamp(3) != null){
			myComp.setIntroduced(new DateTime(resultSet.getTimestamp(3)));
		}
		if(resultSet.getTimestamp(4) != null){
			myComp.setDiscontinued(new DateTime(resultSet.getTimestamp(4)));
		}
		myComp.setCompany(new Company(resultSet.getLong(5),resultSet.getString(6)));
		return myComp;
	}
	
}
