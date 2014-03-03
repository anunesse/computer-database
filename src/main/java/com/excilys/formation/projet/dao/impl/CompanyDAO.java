package com.excilys.formation.projet.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.dao.ICompanyDAO;
import com.excilys.formation.projet.om.Company;

@Repository
public class CompanyDAO implements ICompanyDAO {
	static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);

	@Autowired
	private BasicDataSource dataSource;
	
	public CompanyDAO() {
		super();
	}

	/**
	 * Retrieve single Company on ID
	 */
	public Company read(long id) {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = null;
		try {
			myCon = dataSource.getConnection();
		} catch (SQLException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "SELECT * FROM company WHERE id = " + id;
		try {
			myStatement = myCon.createStatement();
			myResults = myStatement.executeQuery(query);
			LOG.info("Companies retrieved.");
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		}

		try {
			if (myResults.first()) {
				try {
					return new Company(myResults.getLong(1),
							myResults.getString(2));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		} finally {
			CloseResults(myResults);
			CloseStatement(myStatement);
		}
		return null;
	}

	/**
	 * Retrieve all companies with max limit for convenient output.
	 */
	public List<Company> read(int max) {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = null;
		try {
			myCon = dataSource.getConnection();
		} catch (SQLException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}
		List<Company> myCompanies = new ArrayList<Company>();
		String query = "SELECT * FROM company LIMIT " + max;

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
					myCompanies.add(new Company(myResults.getInt(1), myResults
							.getString(2)));
				}
				LOG.info("Companies retrieved.");
				return myCompanies;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				CloseResults(myResults);
				CloseStatement(myStatement);
			}
		}
		return null;
	}

	/**
	 * Default companies retriever.
	 */
	public List<Company> read() {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = null;
		try {
			myCon = dataSource.getConnection();
		} catch (SQLException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}
		List<Company> myCompanies = new ArrayList<Company>();
		String query = "SELECT * FROM company";

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
					myCompanies.add(new Company(myResults.getInt(1), myResults
							.getString(2)));
				}
				LOG.info("Companies retrieved.");
				return myCompanies;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				CloseResults(myResults);
				CloseStatement(myStatement);
			}
		}
		return null;
	}

	/**
	 * Test if ID exist on table.
	 */
	public boolean exist(long id) {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = null;
		try {
			myCon = dataSource.getConnection();
		} catch (SQLException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "SELECT id, name FROM company WHERE id = " + id;
		try {
			myStatement = myCon.createStatement();
			myResults = myStatement.executeQuery(query);
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		}

		try {
			if (myResults.first())
				return true;
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		} finally {
			CloseResults(myResults);
			CloseStatement(myStatement);
		}
		return false;
	}

	
	public void CloseResults(ResultSet myResults) {
		try {
			myResults.close();
		} catch (SQLException e) {
			LOG.error("[CLOSE SQLEXCEPTION]");
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("[CLOSE NPEXCEPTION]");
			e.printStackTrace();
		}
	}

	public void CloseStatement(Statement myStatement) {
		try {
			myStatement.close();
		} catch (SQLException e) {
			LOG.error("[CLOSE SQLEXCEPTION]");
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("[CLOSE NPEXCEPTION]");
			e.printStackTrace();
		}
	}
}
