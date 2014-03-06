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

import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAO implements IComputerDAO {
	static final Logger LOG = LoggerFactory.getLogger(ComputerDAO.class);

	@Autowired
	private BoneCPDataSource dataSource;
	
	public ComputerDAO() {
		super();
	}

	/**
	 * Return COUNT(*)
	 */
	public int readTotal() {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "SELECT COUNT(*) FROM computer";
		try {
			myStatement = myCon.createStatement();
			myResults = myStatement.executeQuery(query);
			if (myResults.first()) {
				LOG.info("Reading number of fields from computer : "
						+ myResults.getInt(1));
				return myResults.getInt(1);
			}
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		} finally {
			closeResults(myResults);
			closeStatement(myStatement);
		}
		return 0;
	}

	/**
	 * Read single computer on ID
	 */
	// @Override
	public Computer read(long id) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c LEFT JOIN company b ON c.company_id = b.id WHERE c.id = ?";

		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setLong(1, id);
			myResults = myStatement.executeQuery();
			if (myResults.first()) {
				LOG.info("Computer retrieved.");
				DateTime date_intr = null;
				if(myResults.getTimestamp(3) != null)
					date_intr = new DateTime(myResults.getTimestamp(3));
				DateTime date_disc = null;
				if(myResults.getTimestamp(4) != null)
					date_disc = new DateTime(myResults.getTimestamp(4));
				return new Computer(myResults.getInt(1), myResults
						.getString(2), date_intr, date_disc, new Company(myResults.getInt(5),
						myResults.getString(6)));
			}
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		} finally {
			closeResults(myResults);
			closeStatement(myStatement);
		}
		return null;
	}

	/**
	 * Check if ID exist
	 */
	// @Override
	public boolean exist(long id) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "SELECT id FROM computer WHERE id = ?";
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setLong(1, id);
			myResults = myStatement.executeQuery();
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
			closeResults(myResults);
			closeStatement(myStatement);
		}
		return false;
	}

	/**
	 * delete Computer on ID
	 */
	// @Override
	public boolean delete(long id) {
		PreparedStatement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "DELETE FROM computer WHERE id = ?";
		boolean b = false;
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setLong(1, id);
			b = (myStatement.executeUpdate() != 0);
			LOG.info("Computer deleted : " + id);
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			throw new RuntimeException(SQLe);
		} finally {
			closeStatement(myStatement);
		}
		return b;
	}

	// @Override
	public List<Computer> readRangedOrdered(int min, int max, String type,
			String field) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}
		List<Computer> myComputers = new ArrayList<Computer>();

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c LEFT JOIN company b ON c.company_id = b.id ORDER BY ? ? LIMIT ? OFFSET ?";

		try {
			myStatement = myCon.prepareStatement(query);

			myStatement.setString(1, field);
			myStatement.setString(2, type);
			myStatement.setInt(3, min);
			myStatement.setInt(4, max);
			myResults = myStatement.executeQuery();
			LOG.info("Computers retrieved.");
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		}
		if (myResults != null) {
			try {
				while (myResults.next()) {
					DateTime date_intr = null;
					if(myResults.getTimestamp(3) != null)
						date_intr = new DateTime(myResults.getTimestamp(3));
					DateTime date_disc = null;
					if(myResults.getTimestamp(4) != null)
						date_disc = new DateTime(myResults.getTimestamp(4));
					Computer my = new Computer(myResults.getInt(1), myResults
							.getString(2), date_intr, date_disc, new Company(myResults.getInt(5),
							myResults.getString(6)));
					myComputers.add(my);
				}
				return myComputers;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				closeResults(myResults);
				closeStatement(myStatement);
			}
		}
		return null;
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
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}
		List<Computer> myComputers = new ArrayList<Computer>();

		String query = "SELECT c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c LEFT JOIN company b ON c.company_id = b.id WHERE c.name LIKE ? OR b.name LIKE ? ORDER BY "
				+ type + " " + field + " LIMIT ? OFFSET ?";
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, "%" + search + "%");
			myStatement.setString(2, "%" + search + "%");
			myStatement.setInt(3, limit);
			myStatement.setInt(4, offset);
			myResults = myStatement.executeQuery();
			LOG.info("Computers retrieved.");
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION");
			SQLe.printStackTrace();
		}
		if (myResults != null) {
			try {
				while (myResults.next()) {
					DateTime date_intr = null;
					if(myResults.getTimestamp(3) != null)
						date_intr = new DateTime(myResults.getTimestamp(3));
					DateTime date_disc = null;
					if(myResults.getTimestamp(4) != null)
						date_disc = new DateTime(myResults.getTimestamp(4));
					Computer my = new Computer(myResults.getInt(1), myResults
							.getString(2), date_intr, date_disc, new Company(myResults.getInt(5),
							myResults.getString(6)));
					myComputers.add(my);
				}
				return myComputers;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				closeResults(myResults);
				closeStatement(myStatement);
			}
		}
		return null;
	}

	/**
	 * Default read function, used to retrieve all data
	 */
	// @Override
	public List<Computer> readAll() {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}
		List<Computer> myComputers = new ArrayList<Computer>();
		
		DateTime date_intr = null;
		DateTime date_disc = null;

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c LEFT JOIN company b ON c.company_id = b.id ORDER BY c.name";
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
					if(myResults.getTimestamp(3) != null)
						date_intr = new DateTime(myResults.getTimestamp(3));
					else
						date_intr=null;
					if(myResults.getTimestamp(4) != null)
						date_disc = new DateTime(myResults.getTimestamp(4));
					else
						date_disc = null;
					myComputers.add(new Computer(myResults.getInt(1), myResults
							.getString(2), date_intr, date_disc, new Company(myResults.getInt(5),
							myResults.getString(6))));
				}
				LOG.info("Computers retrieved.");
				return myComputers;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				closeResults(myResults);
				closeStatement(myStatement);
			}
		}
		return null;
	}

	/**
	 * Retrieves number of results for research
	 */
	public int readTotal(String search) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = null;
		try {
			myCon = dataSource.getConnection();
		} catch (SQLException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "SELECT COUNT(*) FROM computer c LEFT JOIN company b ON c.company_id = b.id WHERE c.name LIKE ? OR b.name LIKE ?";
		//String query = "SELECT COUNT(*) FROM computer c JOIN company b LEFT ON c.company_id = b.id WHERE c.name LIKE ?";
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, "%" + search + "%");
			myStatement.setString(2, "%" + search + "%");
			myResults = myStatement.executeQuery();
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		}
		if (myResults != null) {
			try {
				if (myResults.first())
					return myResults.getInt(1);
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				closeResults(myResults);
				closeStatement(myStatement);
			}
		}
		return 0;
	}

	// @Override
	public long add(Computer myComp) {
		PreparedStatement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?);";
		try {
			myStatement = myCon.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			myStatement.setString(1, myComp.getName());
			if (myComp.getIntroduced() == null)
				myStatement.setNull(2, java.sql.Types.TIMESTAMP);
			else {
				myStatement.setTimestamp(2, new Timestamp(myComp
						.getIntroduced().getMillis()));
			}
			if (myComp.getDiscontinued() == null)
				myStatement.setNull(3, java.sql.Types.TIMESTAMP);
			else {
				myStatement.setTimestamp(3, new Timestamp(myComp
						.getDiscontinued().getMillis()));
			}
			if(myComp.getCompany().getId()==0){
				myStatement.setNull(4, java.sql.Types.BIGINT);
			}
			else{
				myStatement.setLong(4, myComp.getCompany().getId());
			}
			myStatement.executeUpdate();
			ResultSet re = myStatement.getGeneratedKeys();
			if (re != null && re.next())
				return re.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			closeStatement(myStatement);
		}
		return 0;
	}
	
	/**
	 * Default editor.
	 */
	// @Override
	public boolean edit(Computer myComp) {
		PreparedStatement myStatement = null;
		Connection myCon = null;
		try {
			myCon = DataSourceUtils.getConnection(dataSource);
		} catch (CannotGetJdbcConnectionException e) {
			LOG.error("Unable to get transaction from transaction manager.");
			e.printStackTrace();
		}

		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, myComp.getName());
			if (myComp.getIntroduced() == null)
				myStatement.setNull(2, java.sql.Types.TIMESTAMP);
			else {
				myStatement.setTimestamp(2, new Timestamp(myComp
						.getIntroduced().getMillis()));
			}
			if (myComp.getDiscontinued() == null)
				myStatement.setNull(3, java.sql.Types.TIMESTAMP);
			else {
				myStatement.setTimestamp(3, new Timestamp(myComp
						.getDiscontinued().getMillis()));
			}
			myStatement.setLong(4, myComp.getCompany().getId());
			myStatement.setLong(5, myComp.getId());
			myStatement.executeUpdate();
			LOG.debug("Computer edited : " + myComp.getId());
			return true;
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			LOG.debug("DATA NOT EDITED");
			throw new RuntimeException(e);
		} finally {
			closeStatement(myStatement);
		}
		//return false;
	}

	public void closeResults(ResultSet myResults) {
		try {
			myResults.close();
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("[NPEXCEPTION]");
			e.printStackTrace();
		}
	}

	public void closeStatement(Statement myStatement) {
		try {
			myStatement.close();
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("[NPEXCEPTION]");
			e.printStackTrace();
		}
	}

}
