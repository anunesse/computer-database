package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOFactory {
	private static BoneCP connectionPool;

	private static ThreadLocal<Connection> localConnection;

	static final Logger LOG = LoggerFactory.getLogger(DAOFactory.class);

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "jee-cdb";
	private static String passwd = "password";

	public DAOFactory(){
		super();
	};
	
	static{
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

	public static Connection getConnection() {

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
			localConnection.set(DAOFactory.getConnection());
			localConnection.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			LOG.error("CANNOT GET CONNECTION FROM NULL DAOFACTORY!");
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
