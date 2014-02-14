package com.excilys.formation.projet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.formation.projet.OM.Company;
import com.excilys.formation.projet.OM.Computer;
import com.excilys.formation.projet.util.Converter;

public class ComputerDAO {

	public ComputerDAO() {
		super();
	}
	
	public int readTotal(){
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
		String query = "SELECT COUNT(*) FROM computer";
		try{
			myStatement = myCon.createStatement();
			myResults = myStatement.executeQuery(query);
			if(myResults.first())
			{
				return myResults.getInt(1);
			}
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		finally{
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return 0;
	}
	
	public Computer read(long id){
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id WHERE c.id = ?";
		
		try{
			myStatement = myCon.prepareStatement(query);
			myStatement.setLong(1, id);
			myResults = myStatement.executeQuery();
			if(myResults.first())
			{
				return new Computer(myResults.getInt(1),
						myResults.getString(2),
						myResults.getTimestamp(3),
						myResults.getTimestamp(4),
						new Company(myResults.getInt(5),myResults.getString(6)));
			}
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}finally{
			CloseResults(myResults);
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return null;
	}
	
	public boolean exist(long id)
	{
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
		String query = "SELECT id FROM computer WHERE id = ?";
		try{
			myStatement = myCon.prepareStatement(query);
			myStatement.setLong(1, id);
			myResults = myStatement.executeQuery();
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
	
	public boolean delete(long id)
	{
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
		String query = "DELETE FROM computer WHERE id = ?";
		boolean b = false;
		try{
			myStatement = myCon.prepareStatement(query);
			myStatement.setLong(1, id);

			b = (myStatement.executeUpdate() != 0);
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION DEL_COMPUTERS]");
			SQLe.printStackTrace();
		}finally{
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return b;
	}
	
	public List<Computer> readName(int max, String name){
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();

		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id WHERE c.name LIKE ? LIMIT ?;";
		
		System.out.println(query);
		try{
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, "%"+name+"%");
			myStatement.setInt(2, max);
			myResults = myStatement.executeQuery();
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		if(myResults != null)
		{
			try {
				while(myResults.next())
				{
					Date d1;
					d1=(myResults.getTimestamp(3) == null)?new Date(258969856):Converter.dateFromTimestamp(myResults.getTimestamp(3));
					Date d2;
					d2=(myResults.getTimestamp(4) == null)?new Date(258969856):Converter.dateFromTimestamp(myResults.getTimestamp(4));
					myComputers.add(new Computer(myResults.getInt(1),
							myResults.getString(2),
							d1,
							d2,
							new Company(myResults.getInt(5),myResults.getString(6))));
				}
				return myComputers;
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
	
	public List<Computer> readRanged(int min, int max){
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();
		
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id ORDER BY c.name LIMIT ? OFFSET ?";
		
		System.out.println(query);
		try{
			myStatement = myCon.prepareStatement(query);
			myStatement.setInt(1, max);
			myStatement.setInt(2, min);
			myResults = myStatement.executeQuery();
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		if(myResults != null)
		{
			try {
				while(myResults.next())
				{
					myComputers.add(new Computer(myResults.getInt(1),
							myResults.getString(2),
							myResults.getTimestamp(3),
							myResults.getTimestamp(4),
							new Company(myResults.getInt(5),myResults.getString(6))));
				}
				return myComputers;
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
	
	public List<Computer> readRangedOrdered(int min, int max, boolean type, String field){
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();
		
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id ORDER BY ? ? LIMIT ? OFFSET ?";
		
		System.out.println(query);
		try{
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, field);
			myStatement.setString(2, (type)?"ASC":"DESC");
			myStatement.setInt(3, max);
			myStatement.setInt(4, min);
			myResults = myStatement.executeQuery();
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		if(myResults != null)
		{
			try {
				while(myResults.next())
				{
					myComputers.add(new Computer(myResults.getInt(1),
							myResults.getString(2),
							myResults.getTimestamp(3),
							myResults.getTimestamp(4),
							new Company(myResults.getInt(5),myResults.getString(6))));
				}
				return myComputers;
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
	
	public List<Computer> readRangedOrderedSearchComputer(int min, int max, boolean type, String field, String search){
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();
		
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id WHERE c.name LIKE ? ORDER BY ? ? LIMIT ? OFFSET ?";
		
		System.out.println(query);
		try{
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, "%"+search+"%");
			myStatement.setString(2, field);
			myStatement.setString(3, (type)?"ASC":"DESC");
			myStatement.setInt(4, max);
			myStatement.setInt(5, min);
			myResults = myStatement.executeQuery();
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		if(myResults != null)
		{
			try {
				while(myResults.next())
				{
					myComputers.add(new Computer(myResults.getInt(1),
							myResults.getString(2),
							myResults.getTimestamp(3),
							myResults.getTimestamp(4),
							new Company(myResults.getInt(5),myResults.getString(6))));
				}
				return myComputers;
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
	
	public List<Computer> readAll(){
		ResultSet myResults = null;
		Statement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		List<Computer> myComputers = new ArrayList<Computer>();
		
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id ORDER BY c.name";
		
		System.out.println(query);
		try{
			myStatement = myCon.createStatement();
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
					myComputers.add(new Computer(myResults.getInt(1),
							myResults.getString(2),
							myResults.getTimestamp(3),
							myResults.getTimestamp(4),
							new Company(myResults.getInt(5),myResults.getString(6))));
				}
				return myComputers;
			} catch (SQLException e) {
				System.out.println("[SQLEXCEPTION PRINT_COMPUTERS]");
				e.printStackTrace();
			}
			finally{
				CloseResults(myResults);
				CloseStatement(myStatement);
				CloseConnection(myCon);
			}
		}
		return null;
	}
	
	public boolean add(Computer myComp){
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?);";
		try {
			myStatement = myCon.prepareStatement(query);
			myStatement.setString(1, myComp.getName());
			myStatement.setTimestamp(2, new Timestamp(myComp.getIntroduced().getTime()));
			myStatement.setTimestamp(3, new Timestamp(myComp.getDiscontinued().getTime()));
			myStatement.setLong(4,myComp.getCompany().getId());
			myStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			CloseStatement(myStatement);
			CloseConnection(myCon);
		}
		return false;
	}
	
	public boolean edit(Computer myComp){
		ResultSet myResults = null;
		PreparedStatement myStatement = null;
		Connection myCon = DAOFactory.getInstance().getConnection();
		
		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
		
		System.out.println("Data  : "+query);
		try {
			myStatement = myCon.prepareStatement(query);
			
			myStatement.setString(1, myComp.getName());
			System.out.println(myComp.getName());
			
			myStatement.setTimestamp(2, new Timestamp(myComp.getIntroduced().getTime()));
			System.out.println(new Timestamp(myComp.getIntroduced().getTime()));
			
			myStatement.setTimestamp(3, new Timestamp(myComp.getDiscontinued().getTime()));
			System.out.println(new Timestamp(myComp.getIntroduced().getTime()));
			
			myStatement.setLong(4, myComp.getCompany().getId());
			System.out.println(myComp.getCompany().getId());
			
			myStatement.setLong(5, myComp.getId());
			System.out.println(myComp.getId());
			
			myStatement.executeUpdate();
			System.out.println("Data edited!");
			return true;
		} catch (SQLException e) {
			System.out.println("Data not edited!");
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
