package com.excilys.formation.projet.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOTest {
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "jee-cdb";
	private static String passwd = "password";
	static final Logger LOG = LoggerFactory.getLogger(DAOTest.class);

	public static void main(String[] args) {
		BoneCP connectionPool = null;
		Connection myCon1 = null;
		Connection myCon2 = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			// setup the connection pool
			BoneCPConfig config = new BoneCPConfig();

			config.setJdbcUrl(url);
			config.setUsername(user);
			config.setPassword(passwd);

			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);

			connectionPool = new BoneCP(config); // setup the connection pool

			myCon1 = connectionPool.getConnection(); // fetch a connection
			myCon2 = connectionPool.getConnection(); // fetch a 2nd connection

			if (myCon2 != null) {
				LOG.info("Connection2 successful!");
				Statement stmt = myCon2.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM computer WHERE id < 4");
				while (rs.next()) {
					LOG.debug(rs.getString(2));
				}
			}

			if (myCon1 != null) {
				LOG.info("Connection1 successful!");
				Statement stmt = myCon1.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM company WHERE id < 4");
				while (rs.next()) {
					LOG.debug(rs.getString(2));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myCon1 != null) {
				try {
					myCon1.close();
					LOG.debug("closing myCon1");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (myCon2 != null) {
				try {
					myCon2.close();
					LOG.debug("closing myCon2");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			connectionPool.shutdown();
		}

	}
}
