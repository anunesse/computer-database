package com.excilys.formation.projet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
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
	private ResultSet myResults;
	private Connection myCon;
	private Statement myStatement;
	private List<Computer> myComputers;
	
	private CompanyDAO myCompanyDAO;

	public ComputerDAO() {
		super();
		//myCompanyDAO = new CompanyDAO();
		myComputers = new ArrayList<Computer>();
	}
	
	public void clearMyComputers(){
		myComputers.clear();
	}
	
	public int getTotalComputer(){
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		
		String query = "SELECT COUNT(*) FROM computer";
		
		try{
			myStatement = myCon.createStatement();
			//System.out.println(myStatement);
			myResults = myStatement.executeQuery(query);
			if(myResults.first())
			{
				return myResults.getInt(1);
			}
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		return 0;
	}
	
	public Computer getComputerById(long id){
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id WHERE c.id = "+id;
		
		try{
			myStatement = myCon.createStatement();
			//System.out.println(myStatement);
			myResults = myStatement.executeQuery(query);
			if(myResults.first())
			{
				return new Computer(myResults.getInt(1),
						myResults.getString(2),
						myResults.getTimestamp(3),
						myResults.getTimestamp(4),
						myResults.getInt(5),
						new Company(myResults.getInt(5),myResults.getString(6)));
			}
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION GET_COMPUTERS]");
			SQLe.printStackTrace();
		}
		return null;
	}
	
	public boolean existComputerId(long id)
	{
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		String query = "SELECT id FROM computer WHERE id = "+id;
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
	
	public boolean deleteComputerId(long id)
	{
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		String query = "DELETE FROM computer WHERE id = "+id;
		boolean b = false;
		try{
			myStatement = myCon.createStatement();
			b = (myStatement.executeUpdate(query) != 0);
		}catch(SQLException SQLe){
			System.out.println("[SQLEXCEPTION DEL_COMPUTERS]");
			SQLe.printStackTrace();
		}
		return b;
	}
	
	public void getComputersByName(int max, String name){
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		//String query = "SELECT * FROM computers WHERE name LIKE '%"+name+"%' LIMIT "+max;
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id WHERE c.name LIKE '%"+name+"%' LIMIT "+max;
		
		System.out.println(query);
		try{
			myStatement = myCon.createStatement();
			//System.out.println(myStatement);
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
					Date d1;
					d1=(myResults.getTimestamp(3) == null)?new Date(258969856):Converter.dateFromTimestamp(myResults.getTimestamp(3));
					Date d2;
					d2=(myResults.getTimestamp(4) == null)?new Date(258969856):Converter.dateFromTimestamp(myResults.getTimestamp(4));
					myComputers.add(new Computer(myResults.getInt(1),
							myResults.getString(2),
							d1,
							d2,
							myResults.getInt(5),
							new Company(myResults.getInt(5),myResults.getString(6))));
				}
			} catch (SQLException e) {
				System.out.println("[SQLEXCEPTION PRINT_COMPUTERS]");
				e.printStackTrace();
			}
		}
	}
	public void getComputers(int max){
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		String query = "SELECT  c.id, c.name, c.introduced, c.discontinued, b.id, b.name FROM computer c JOIN company b ON c.company_id = b.id ORDER BY c.name LIMIT "+max;
		
		System.out.println(query);
		ResultSet myResults = null;
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
					myComputers.add(new Computer(myResults.getInt(1),
							myResults.getString(2),
							myResults.getTimestamp(3),
							myResults.getTimestamp(4),
							myResults.getInt(5),
							new Company(myResults.getInt(5),myResults.getString(6))));
				}
			} catch (SQLException e) {
				System.out.println("[SQLEXCEPTION PRINT_COMPUTERS]");
				e.printStackTrace();
			}
		}
	}
	
	public boolean addComputer(Computer myComp){
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES('"+myComp.getName()+"','"+new Timestamp(myComp.getIntroduced().getTime())+"', '"+new Timestamp(myComp.getDiscontinued().getTime())+"', '"+myComp.getCompany_id()+"');";
		System.out.println("Data  : "+query);
		try {
			myStatement = myCon.createStatement();
			myStatement.executeUpdate(query);
			System.out.println("Data inserted!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editComputer(Computer myComp){
		System.out.println("EDITING COMPUTER! OK");
		DAOFactory.getInstance();
		myCon = DAOFactory.getConnection();
		String query = "UPDATE computer SET name ='"+myComp.getName()
				+"', introduced ='"+Converter.timestampFromDate(myComp.getIntroduced())
				+"', discontinued='"+Converter.timestampFromDate(myComp.getDiscontinued())
				+"', company_id ='"+myComp.getCompany_id()+"' WHERE id = '"+myComp.getId()+"';";
		
		System.out.println("Data  : "+query);
		try {
			myStatement = myCon.createStatement();
			myStatement.executeUpdate(query);
			System.out.println("Data edited!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Computer> getMyComputers() {
		return myComputers;
	}

	public void setMyComputers(List<Computer> myComputers) {
		this.myComputers = myComputers;
	}

	public void CloseConnection(){
		try {
			myCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			System.out.println("UNABLE TO CLOSE ALL RESSOURCES");
		}
		System.out.println("Connection successfully closed");
	}
	
}
