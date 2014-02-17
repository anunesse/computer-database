package com.excilys.formation.projet.dao;

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

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.util.Converter;

public class ComputerDAO implements IComputerDAO {
	static final Logger LOG = LoggerFactory.getLogger(ComputerDAO.class);

	public ComputerDAO() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.formation.projet.dao.IDAO#readTotal()
	 */
	@Override
	public int readTotal() {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();

		String query = "SELECT COUNT(*) FROM computer";
		LOG.debug(query);
		try {
			myStatement = myCon.createStatement();
			myResults = myStatement.executeQuery(query);
			if (myResults.first()) {
				return myResults.getInt(1);
			}
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		} finally {
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.formation.projet.dao.IDAO#read(long)
	 */
	@Override
	public Computer read(long id) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id WHERE c.id = ?";

		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setLong(1, id);
			myResults = myStatement.executeQuery();
			if (myResults.first()) {
				return new Computer(myResults.getInt(1),
						myResults.getString(2), myResults.getTimestamp(3),
						myResults.getTimestamp(4), new Company(
								myResults.getInt(5), myResults.getString(6)));
			}
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		} finally {
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.formation.projet.dao.IDAO#exist(long)
	 */
	@Override
	public boolean exist(long id) {
		int page = 1;
		int limit = 20;
		int offset = 0;
		String searchComputer = "";
		String searchCompany = "";
		String sortResult = "";
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();

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
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.formation.projet.dao.IDAO#delete(long)
	 */
	@Override
	public boolean delete(long id) {
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();

		String query = "DELETE FROM computer WHERE id = ?";
		boolean b = false;
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setLong(1, id);

			b = (myStatement.executeUpdate() != 0);
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		} finally {
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.formation.projet.dao.IDAO#readName(int,
	 * java.lang.String)
	 */
	@Override
	public List<Computer> readName(int max, String name) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id WHERE c.name LIKE ? LIMIT ?;";

		System.out.println(query);
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, "%" + name + "%");
			myStatement.setInt(2, max);
			myResults = myStatement.executeQuery();
		} catch (SQLException SQLe) {
			System.out.println("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		}
		if (myResults != null) {
			try {
				while (myResults.next()) {
					Date d1;
					d1 = (myResults.getTimestamp(3) == null) ? new Date(
							258969856) : Converter.dateFromTimestamp(myResults
							.getTimestamp(3));
					Date d2;
					d2 = (myResults.getTimestamp(4) == null) ? new Date(
							258969856) : Converter.dateFromTimestamp(myResults
							.getTimestamp(4));
					myComputers.add(new Computer(myResults.getInt(1), myResults
							.getString(2), d1, d2, new Company(myResults
							.getInt(5), myResults.getString(6))));
				}
				return myComputers;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.formation.projet.dao.IDAO#readRanged(int, int)
	 */
	@Override
	public List<Computer> readRanged(int min, int max) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id ORDER BY c.name LIMIT ? OFFSET ?";
		System.out.println(query);
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setInt(1, min);
			myStatement.setInt(2, max);
			myResults = myStatement.executeQuery();
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		}
		if (myResults != null) {
			try {
				while (myResults.next()) {
					myComputers.add(new Computer(myResults.getInt(1), myResults
							.getString(2), myResults.getTimestamp(3), myResults
							.getTimestamp(4), new Company(myResults.getInt(5),
							myResults.getString(6))));
				}
				return myComputers;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.formation.projet.dao.IDAO#readRangedOrdered(int, int,
	 * boolean, java.lang.String)
	 */
	@Override
	public List<Computer> readRangedOrdered(int min, int max, String type,
			String field) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id ORDER BY ? ? LIMIT ? OFFSET ?";

		System.out.println(query);
		try {
			myStatement = myCon.prepareStatement(query);

			myStatement.setString(1, field);
			myStatement.setString(2, type);
			myStatement.setInt(3, min);
			myStatement.setInt(4, max);
			myResults = myStatement.executeQuery();
		} catch (SQLException SQLe) {
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		if (myResults != null) {
			try {
				while (myResults.next()) {
					Computer my = new Computer(myResults.getInt(1),
							myResults.getString(2), myResults.getTimestamp(3),
							myResults.getTimestamp(4),
							new Company(myResults.getInt(5), myResults
									.getString(6)));
					myComputers.add(my);
					// LOG.debug(my.toString());
				}
				return myComputers;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.formation.projet.dao.IDAO#readRangedOrderedSearchComputer
	 * (int, int, boolean, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Computer> readRangedOrderedSearchComputer(int min, int max,
			boolean type, String field, String search) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id WHERE c.name LIKE ? ORDER BY ? ? LIMIT ? OFFSET ?";

		System.out.println(query);
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, "%" + search + "%");
			myStatement.setString(2, field);
			myStatement.setString(3, (type) ? "ASC" : "DESC");
			myStatement.setInt(4, max);
			myStatement.setInt(5, min);
			myResults = myStatement.executeQuery();
		} catch (SQLException SQLe) {
			LOG.error("[SQLEXCEPTION]");
			SQLe.printStackTrace();
		}
		if (myResults != null) {
			try {
				while (myResults.next()) {
					myComputers.add(new Computer(myResults.getInt(1), myResults
							.getString(2), myResults.getTimestamp(3), myResults
							.getTimestamp(4), new Company(myResults.getInt(5),
							myResults.getString(6))));
				}
				return myComputers;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.formation.projet.dao.IDAO#readAll()
	 */
	@Override
	public List<Computer> readAll() {
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id ORDER BY c.name";

		System.out.println(query);
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
					myComputers.add(new Computer(myResults.getInt(1), myResults
							.getString(2), myResults.getTimestamp(3), myResults
							.getTimestamp(4), new Company(myResults.getInt(5),
							myResults.getString(6))));
				}
				return myComputers;
			} catch (SQLException e) {
				LOG.error("[SQLEXCEPTION]");
				e.printStackTrace();
			} finally {
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.formation.projet.dao.IDAO#add(com.excilys.formation.projet
	 * .om.Computer)
	 */
	@Override
	public boolean add(Computer myComp) {
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();

		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?);";
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, myComp.getName());
			myStatement.setTimestamp(2, new Timestamp(myComp.getIntroduced()
					.getTime()));
			myStatement.setTimestamp(3, new Timestamp(myComp.getDiscontinued()
					.getTime()));
			myStatement.setLong(4, myComp.getCompany().getId());
			myStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.formation.projet.dao.IDAO#edit(com.excilys.formation.projet
	 * .om.Computer)
	 */
	@Override
	public boolean edit(Computer myComp) {
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();

		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

		System.out.println("Data  : " + query);
		try {
			myStatement = myCon.prepareStatement(query);

			myStatement.setString(1, myComp.getName());
			System.out.println(myComp.getName());

			myStatement.setTimestamp(2, new Timestamp(myComp.getIntroduced()
					.getTime()));
			System.out.println(new Timestamp(myComp.getIntroduced().getTime()));

			myStatement.setTimestamp(3, new Timestamp(myComp.getDiscontinued()
					.getTime()));
			System.out.println(new Timestamp(myComp.getIntroduced().getTime()));

			myStatement.setLong(4, myComp.getCompany().getId());
			System.out.println(myComp.getCompany().getId());

			myStatement.setLong(5, myComp.getId());
			System.out.println(myComp.getId());

			myStatement.executeUpdate();
			LOG.debug("DATA EDITED");
			return true;
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			LOG.debug("DATA NOT EDITED");
			e.printStackTrace();
		} finally {
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return false;
	}

	public void CloseConnection(Connection myCon) {
		try {
			myCon.close();
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		} catch (NullPointerException e) {
			LOG.error("[NPEXCEPTION]");
			e.printStackTrace();
		}
		System.out.println("Connection successfully closed");
	}

	public void CloseResults(ResultSet myResults) {
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

	public void CloseStatement(Statement myStatement) {
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
