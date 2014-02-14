package com.excilys.formation.projet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
	private static CompanyDAO myCompanyDAO;
	private static ComputerDAO myComputerDAO;
	
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "jee-cdb";
	private static String passwd = "password";
	
	private static DAOFactory myDAO;
	
	private DAOFactory(){
		myCompanyDAO = new CompanyDAO();
		myComputerDAO = new ComputerDAO();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Connection successfully openned");
		} 
		catch (ClassNotFoundException CNFe) {
			System.out.println("[CLASSNFEXCEPTION INIT]");
			CNFe.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static DAOFactory getInstance(){
		if(myDAO == null)
			myDAO = new DAOFactory();
		return myDAO;
	}

	public static CompanyDAO getMyCompanyDAO() {
		return myCompanyDAO;
	}

	public static ComputerDAO getMyComputerDAO() {
		return myComputerDAO;
	}
	
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(url,user,passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
