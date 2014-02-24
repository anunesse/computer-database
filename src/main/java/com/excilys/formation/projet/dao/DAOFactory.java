package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

@Repository
public class DAOFactory {
	private static BoneCP connectionPool;

	private static ThreadLocal<Connection> localConnection;

	static final Logger LOG = LoggerFactory.getLogger(DAOFactory.class);

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "jee-cdb";
	private static String passwd = "password";

	private static DAOFactory myDAO;

	private DAOFactory() {
		localConnection = new ThreadLocal<Connection>();

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

	/*public static ICompanyDAO getMyCompanyDAO() {
		return companyDAO;
	}

	public static IComputerDAO getMyComputerDAO() {
		return computerDAO;
	}

	public static ILogDAO getMyLogDAO() {
		return logDAO;
	}*/

	public Connection getConnection() {

		if (localConnection.get() == null) {
			try {
				LOG.debug("[BONECP] RETURNING CONNECTION");
				localConnection.set(connectionPool.getConnection());
				return localConnection.get();
			} catch (SQLException e) {
				LOG.error("[UNABLE TO GET CONNECTION]");
				e.printStackTrace();
			}
		} else {
			LOG.debug("[RETURNING THREAD CONNECTION]");
			return localConnection.get();
		}
		return null;
	}

	public static void startTransaction() {
		try {
			localConnection.set(myDAO.getConnection());
			localConnection.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeTransaction() {
		try {
			localConnection.get().setAutoCommit(true);
			localConnection.get().close();
			localConnection.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
