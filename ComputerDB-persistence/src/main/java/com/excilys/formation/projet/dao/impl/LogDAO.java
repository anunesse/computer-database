package com.excilys.formation.projet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.om.Log;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class LogDAO implements ILogDAO {
	static final Logger LOG = LoggerFactory.getLogger(LogDAO.class);

	@Autowired
	private BoneCPDataSource dataSource;
	
	// @Override
	public List<Log> readAll() {
		ResultSet myResults = null;
		Statement myStatement = null;

		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}
		
		List<Log> myLogs = new ArrayList<Log>();

		String query = "SELECT * FROM log ORDER BY opdate DESC";

		try {
			myStatement = myCon.createStatement();
			myResults = myStatement.executeQuery(query);
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		}
		if (myResults != null) {
			try {
				while (myResults.next()) {
					myLogs.add(new Log(myResults.getString("optype"), new DateTime(
							myResults.getTimestamp("opdate")),
							myResults.getString("description")));
				}
				return myLogs;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION 1]");
				e.printStackTrace();
			} finally {
				closeResults(myResults);
				closeStatement(myStatement);
			}
		}
		return null;
	}

	// @Override
	public boolean create(Log log) {
		LOG.debug("[SQLEXCEPTION 2]");
		PreparedStatement myStatement = null;

		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}
		
		String query = "INSERT INTO log (optype, opdate, description) VALUES(?,?,?);";
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, log.getOperationType());
			myStatement.setTimestamp(2, new Timestamp(log.getOperationDate()
					.getMillis()));
			myStatement.setString(3, log.getDescription());
			myStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION 2]");
			e.printStackTrace();
		} finally {
			closeStatement(myStatement);
		}
		return false;
	}

	public void closeResults(ResultSet myResults) {
		try {
			myResults.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("UNABLE TO CLOSE RESULTSET");
			e.printStackTrace();
		}
	}

	public void closeStatement(Statement myStatement) {
		try {
			myStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("UNABLE TO CLOSE STATEMENT");
			e.printStackTrace();
		}
	}
}