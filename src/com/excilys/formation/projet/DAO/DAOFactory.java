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
	private static Connection myCon;
	
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
		if(myDAO == null){
			myDAO = new DAOFactory();
		}
		try {
			myCon = DriverManager.getConnection(url,user,passwd);
			return myDAO;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static CompanyDAO getMyCompanyDAO() {			// TODO Auto-generated catch block
		return myCompanyDAO;
	}

	public static ComputerDAO getMyComputerDAO() {
		return myComputerDAO;
	}
	
	public static Connection getConnection() {
		return myCon;
	}
	
}
