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

	public CompanyDAO() {
		super();
	}
	
	public String readString(long id){
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
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
		}finally{
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return "Unknown company";
	}
	
	public Company read(long id){
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
		String query = "SELECT * FROM company WHERE id = "+id;
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
					return new Company(myResults.getLong(1),myResults.getString(2));	
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return null;
	}

	public List<Company> read(int max){
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Company> myCompanies = new ArrayList<Company>();
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
					myCompanies.add(new Company(myResults.getInt(1),myResults.getString(2)));
				}
				return myCompanies;
			} catch (SQLException e) {
				System.out.println("[SQLEXCEPTION PRINT_COMPUTERS]");
				e.printStackTrace();
			}finally{
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}
	
	public List<Company> read(){
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Company> myCompanies = new ArrayList<Company>();
		String query = "SELECT * FROM company";
		
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
					myCompanies.add(new Company(myResults.getInt(1),myResults.getString(2)));
				}
				return myCompanies;
			} catch (SQLException e) {
				System.out.println("[SQLEXCEPTION PRINT_COMPUTERS]");
				e.printStackTrace();
			}finally{
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}

	public boolean exist(long id)
	{
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
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
		}finally{
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return false;
	}
	
	public void CloseConnection(Connection myCon){
		try {
			myCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			System.out.println("UNABLE TO CLOSE CONNECTION");
		}
		System.out.println("Connection successfully closed");
	}
	public void CloseResults(ResultSet myResults){
		try {
			myResults.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			System.out.println("UNABLE TO CLOSE RESULTSET");
		}
	}
	public void CloseStatement(Statement myStatement){
		try {
			myStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			System.out.println("UNABLE TO CLOSE STATEMENT");
		}
	}
}
