package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.projet.dao.impl.CompanyDAO;
import com.excilys.formation.projet.dao.impl.ComputerDAO;
import com.excilys.formation.projet.dao.impl.LogDAO;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOFactory {

	private static ICompanyDAO myCompanyDAO;
	private static IComputerDAO myComputerDAO;
	private static ILogDAO myLogDAO;

	private static BoneCP connectionPool;

	static final Logger LOG = LoggerFactory.getLogger(DAOFactory.class);

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "jee-cdb";
	private static String passwd = "password";

	private static DAOFactory myDAO;

	private DAOFactory() {
		myCompanyDAO = new CompanyDAO();
		myComputerDAO = new ComputerDAO();
		myLogDAO = new LogDAO();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			LOG.info("[BONECP]Connection successfully openned");
		} catch (ClassNotFoundException CNFe) {
			LOG.error("[CLASSNFEXCEPTION]");
			CNFe.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		BoneCPConfig config = new BoneCPConfig();

		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setPassword(passwd);

		config.setMinConnectionsPerPartition(5);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(1);
		try {
			connectionPool = new BoneCP(config);
		} catch (SQLException e) {
			LOG.error("[SQLEXCEPTION]");
			e.printStackTrace();
		}
	}

	public static DAOFactory getInstance() {
		if (myDAO == null)
			myDAO = new DAOFactory();
		return myDAO;
	}

	public static ICompanyDAO getMyCompanyDAO() {
		return myCompanyDAO;
	}

	public static IComputerDAO getMyComputerDAO() {
		return myComputerDAO;
	}

	public static ILogDAO getMyLogDAO() {
		return myLogDAO;
	}

	public Connection getConnection() {
		try {
			LOG.info("[BONECP] RETURNING CONNECTION");
			return connectionPool.getConnection();
		} catch (SQLException e) {
			LOG.error("[UNABLE TO GET CONNECTION]");
			e.printStackTrace();
		}
		return null;
	}

}
