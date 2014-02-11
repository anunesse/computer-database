package com.excilys.formation.projet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.projet.OM.Company;
import com.excilys.formation.projet.OM.Computer;

public class CompanyDAO {
	private ResultSet myResults;
	private Connection myCon;
	private Statement myStatement;
	private List<Company> myCompanys; 

	public CompanyDAO() {
		super();
		myCompanys = new ArrayList<Company>();
	}
	
	public String getCompanyById(long id){
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		String query = "SELECT name FROM company WHERE id = "+id;
		try{
			myStatement = myCon.createStatement();
			myResults = myStatement.executeQuery(query);
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		
		try {
			if(myResults.first())
			{
				try {
					return myResults.getString("name");	
				} catch (SQLException e) {
					e.printStackTrace();
					return "Unknow company";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Unknown company";
	}
	
	public void clearCompanys(){
		myCompanys.clear();
	}
	
	public List<Company> getMyCompanys() {
		return myCompanys;
	}

	public void setMyCompanys(List<Company> myCompanys) {
		this.myCompanys = myCompanys;
	}

	public void getCompanys(long max){
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		String query = "SELECT * FROM company LIMIT "+max;
		
		try{
			myStatement = myCon.createStatement();
			System.out.println(myStatement);
			myResults = myStatement.executeQuery(query);
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		if(myResults != null)
		{
			try {
				while(myResults.next())
				{
					myCompanys.add(new Company(myResults.getInt(1),myResults.getString(2)));
				}
			} catch (SQLException e) {
				System.out.println("[SQLEXCEPTION PRINT_COMPUTERS]");
				e.printStackTrace();
			}
		}
	}

	public boolean existCompanyId(long id)
	{
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		String query = "SELECT id, name FROM company WHERE id = "+id;
		try{
			myStatement = myCon.createStatement();
			myResults = myStatement.executeQuery(query);
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		
		try {
			if(myResults.first())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void CloseConnection(){
		try {
			myCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection successfully closed");
	}
}
