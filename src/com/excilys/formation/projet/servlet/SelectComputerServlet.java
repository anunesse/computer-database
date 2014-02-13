package com.excilys.formation.projet.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.projet.DAO.CompanyDAO;
import com.excilys.formation.projet.DAO.ComputerDAO;
import com.excilys.formation.projet.DAO.DAOFactory;
import com.excilys.formation.projet.OM.Company;
import com.excilys.formation.projet.OM.Computer;
import com.excilys.formation.projet.util.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Servlet implementation class SelectComputerServlet
 */
@WebServlet("/SelectComputerServlet")
public class SelectComputerServlet extends HttpServlet {
	
	final Logger logger = LoggerFactory.getLogger(SelectComputerServlet.class);
	
	private ComputerDAO myComputerDAO;
	private CompanyDAO myCompanyDAO;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectComputerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Http Get request catched.");
		DAOFactory.getInstance();
		myCompanyDAO = DAOFactory.getMyCompanyDAO();
		myComputerDAO = DAOFactory.getMyComputerDAO();
		myCompanyDAO.clearCompanys();
		myCompanyDAO.getCompanys(45);
		request.setAttribute("options", myCompanyDAO.getMyCompanys());

		if(request.getParameter("computer") == null || request.getParameter("computer").equals(""))
		{
			//request.getRequestDispatcher("WEB-INF/dasboard.jsp").forward(request, response);
			logger.error("The computer id could not be found on request.");
			request.setAttribute("computer", 1);
		}	
		else
		{
			DAOFactory.getInstance();
			myComputerDAO = DAOFactory.getMyComputerDAO();
			Computer myComp = myComputerDAO.getComputerById(Long.parseLong(request.getParameter("computer")));
			if(myComp == null)
			{
				logger.error("The computer id could not be found on database.");
				request.setAttribute("computer", 1);
			}
			else
			{
				request.setAttribute("computer", myComp);
			}
		}
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Http Post request catched.");
		DAOFactory.getInstance();
		myCompanyDAO = DAOFactory.getMyCompanyDAO();
		myComputerDAO = DAOFactory.getMyComputerDAO();
		
		if(request.getParameter("mode") == null || request.getParameter("mode").equals("")){
			System.out.println("[POSTED] error cant find mode!");
			request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
			return;
		}
		else if(request.getParameter("mode").equals("del"))
		{
			System.out.println("DELETING : "+request.getParameter("computer"));
			if(myComputerDAO.deleteComputerId(Long.parseLong(request.getParameter("computer")))){
				System.out.println("Computer Deleted!");
				request.setAttribute("error", "success");
				request.setAttribute("title", "Computer deleted");
				request.setAttribute("message", "The computer was successfully deleted from the database.");
			}
			else
			{
				request.setAttribute("error", "danger");
				request.setAttribute("title", "Fail to delete computer");
				request.setAttribute("message", "The computer was not deleted to the database please try again.");
			}
			request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
			return;
		}
		else if(request.getParameter("mode").equals("add"))
		{			
			Timestamp ts_introduced = null;
		    Timestamp ts_discontinued = null;
		    Computer myComp = null;
		    boolean has_errors = false;
		    String error_params = "(Errors : ";
		    if(!Validation.validateString(request.getParameter("name"))){
		    		error_params += "\nComputer name failed / ";has_errors=true;
		    }
		    if(!Validation.validateString(request.getParameter("company"))){
		    		error_params += "\nCompany name failed / ";has_errors=true;
		    }
		    if(!Validation.validateString(request.getParameter("introduced"))){
		    		error_params += "\nIntroduced date failed / ";has_errors=true;
		    }
		    if(!Validation.validateString(request.getParameter("discontinued"))){
		    		error_params += "\nDiscontinued date failed";has_errors=true;
		    }
		    error_params = ")";
		    if(has_errors){
		    	request.setAttribute("error", "danger");
				request.setAttribute("title", "Fail to add computer");
				request.setAttribute("message", "The computer was not added to the database please try again. "+error_params);
				request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
				return;
		    }
		    
		    System.out.println(ts_introduced+" __ "+ts_discontinued+"company id = "+request.getParameter("company")+" id2 = "+Long.parseLong(request.getParameter("company")));
			if(myCompanyDAO.existCompanyId(Long.parseLong(request.getParameter("company")))){
				DateFormat formatter;
				Date dateFormattedIntroduced = null;
				Date dateFormattedDiscontinued = null;
				formatter = new SimpleDateFormat("yyyy-mm-dd");
				try {
					dateFormattedIntroduced = (Date)formatter.parse(request.getParameter("introduced"));
					dateFormattedDiscontinued = (Date)formatter.parse(request.getParameter("discontinued"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				myComp = new Computer(request.getParameter("name"), dateFormattedIntroduced, dateFormattedDiscontinued, Long.parseLong(request.getParameter("company")));
			}
			
			if(myComputerDAO.addComputer(myComp))
			{
				request.setAttribute("error", "success");
				request.setAttribute("title", "Computer added");
				request.setAttribute("message", "Congratulations, the computer has been added to the database.");
			}
			else
			{
				request.setAttribute("error", "danger");
				request.setAttribute("title", "Fail to add computer");
				request.setAttribute("message", "The computer was not added to the database please try again.");
			}
		}
		else if(request.getParameter("mode").equals("edit"))
		{
			System.out.println("POSTED : "+
					request.getParameter("name")+" compnay = "+
					request.getParameter("company")+" "+
					request.getParameter("introduced")+
					request.getParameter("discontinued"));
			
			boolean has_errors = false;
		    String error_params = "(Errors : ";
		    
		    if(!Validation.validateString(request.getParameter("name"))){
		    		error_params += "\nComputer name failed / ";has_errors=true;
		    }
		    if(!Validation.validateString(request.getParameter("company"))){
		    		error_params += "\nCompany name failed / ";has_errors=true;
		    }
		    if(!Validation.validateString(request.getParameter("introduced"))){
		    		error_params += "\nIntroduced date failed / ";has_errors=true;
		    }
		    if(!Validation.validateString(request.getParameter("discontinued"))){
		    		error_params += "\nDiscontinued date failed";has_errors=true;
		    }
		    error_params += ")";
		    if(has_errors){
		    	request.setAttribute("error", "danger");
				request.setAttribute("title", "Fail to edit computer");
				request.setAttribute("message", "The computer was not edited to the database please try again. "+error_params);
				request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
				return;
		    }
		    
			DateFormat formatter;
			Date dateFormattedIntroduced = null;
			Date dateFormattedDiscontinued = null;
			formatter = new SimpleDateFormat("yyyy-mm-dd");
			try {
				dateFormattedIntroduced = (Date)formatter.parse(request.getParameter("introduced"));
				dateFormattedDiscontinued = (Date)formatter.parse(request.getParameter("discontinued"));
			} catch (ParseException e1) {
				request.setAttribute("error", "danger");
				request.setAttribute("title", "Fail to edit computer");
				request.setAttribute("message", "The computer was not updated, please try again.");
				e1.printStackTrace();
				return;
			}
			
			Computer myComp = new Computer(Long.parseLong(request.getParameter("id")),
					request.getParameter("name"),
					dateFormattedIntroduced,
					dateFormattedDiscontinued,
					Long.parseLong(request.getParameter("company")));
			
			if(myComputerDAO.editComputer(myComp))
			{
				request.setAttribute("error", "success");
				request.setAttribute("title", "Computer edited");
				request.setAttribute("message", "Congratulations, the computer has been edited and data overwritten.");
			}
			else
			{
				request.setAttribute("error", "danger");
				request.setAttribute("title", "Fail to edit computer");
				request.setAttribute("message", "The computer was not updated, please try again.");
			}
		}
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
	}
}
