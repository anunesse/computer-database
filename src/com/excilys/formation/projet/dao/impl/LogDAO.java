package com.excilys.formation.projet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.ILogDAO;
import com.excilys.formation.projet.om.Log;

public class LogDAO implements ILogDAO {
	static final Logger LOG = LoggerFactory.getLogger(LogDAO.class);

	@Override
	public List<Log> readAll() {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Log> myLogs = new ArrayList<Log>();

		String query = "SELECT * FROM log";

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
					myLogs.add(new Log(myResults.getString("optype"), new Date(
							myResults.getTimestamp("opdate").getTime()),
							myResults.getString("description")));
				}
				return myLogs;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION 1]");
				e.printStackTrace();
			} finally {
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}

	@Override
	public boolean create(Log log) {
		LOG.debug("[SQLEXCEPTION 2]");
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();

		String query = "INSERT INTO log (optype, opdate, description) VALUES(?,?,?);";
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, log.getOperationType());
			myStatement.setTimestamp(2, new Timestamp(log.getOperationDate()
					.getTime()));
			myStatement.setString(3, log.getDescription());
			myStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION 2]");
			e.printStackTrace();
		} finally {
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return false;
	}

	public void CloseConnection(Connection myCon) {
		try {
			myCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("UNABLE TO CLOSE CONNECTION");
			e.printStackTrace();
		}
	}

	public void CloseResults(ResultSet myResults) {
		try {
			myResults.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("UNABLE TO CLOSE RESULTSET");
			e.printStackTrace();
		}
	}

	public void CloseStatement(Statement myStatement) {
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