package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {
	private static BasicDataSource DataSource;
	
	private static ThreadLocal<Connection> localConnection;

	static final Logger LOG = LoggerFactory.getLogger(DAOFactory.class);

	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "jee-cdb";
	private static String passwd = "password";

	public DAOFactory(){
		super();
	};
	
	static{
		DataSource = new BasicDataSource();
		DataSource.setDriverClassName("com.mysql.jdbc.Driver");
		localConnection = new ThreadLocal<Connection>();
		
		DataSource.setUrl(url);
		DataSource.setUsername(user);
		DataSource.setPassword(passwd);
		
		DataSource.setInitialSize(10);
		DataSource.setMaxActive(10);
	}

	public static Connection getConnection() {
		if (localConnection.get() == null) {
			try {
				LOG.debug("[BONECP] RETURNING CONNECTION");
				localConnection.set(DataSource.getConnection());
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
