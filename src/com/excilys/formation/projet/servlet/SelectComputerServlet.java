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

/**
 * Servlet implementation class SelectComputerServlet
 */
@WebServlet("/SelectComputerServlet")
public class SelectComputerServlet extends HttpServlet {
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
		
		DAOFactory.getInstance();
		myCompanyDAO = DAOFactory.getMyCompanyDAO();
		myComputerDAO = DAOFactory.getMyComputerDAO();
		myCompanyDAO.clearCompanys();
		myCompanyDAO.getCompanys(45);
		request.setAttribute("options", myCompanyDAO.getMyCompanys());
		System.out.println("LENGHT : "+myCompanyDAO.getMyCompanys().size());
		
		// TODO Auto-generated method stub
		if(request.getParameter("computer") == null || request.getParameter("computer").equals(""))
		{
			System.out.println("[error] computer servlet");
			request.setAttribute("computer", 1);
		}	
		else
		{
			DAOFactory.getInstance();
			myComputerDAO = DAOFactory.getMyComputerDAO();
			Computer myComp = myComputerDAO.getComputerById(Integer.parseInt(request.getParameter("computer")));
			if(myComp == null)
			{
				System.out.println("[error] computer servlet getting computer by id");
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
		
		DAOFactory.getInstance();
		myCompanyDAO = DAOFactory.getMyCompanyDAO();
		myComputerDAO = DAOFactory.getMyComputerDAO();
		
		// TODO Auto-generated method stub
		//
		//PARSING DATE SERVER SIDE HERE
		//getParameter(continued) & getParameter(discontinued)
		//
		request.setAttribute("insert", "Insertion done!");
		System.out.println("[POSTED]");
		if(request.getParameter("mode") == null || request.getParameter("mode").equals(""))
			System.out.println("[POSTED] error cant find mode!");
		else if(request.getParameter("mode").equals("add"))
		{			
		    //DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.FRANCE);

		    Timestamp ts_introduced = null;
		    Timestamp ts_discontinued = null;
		    ts_introduced = Timestamp.valueOf("1999-02-02 14:14:14");
		    ts_discontinued = Timestamp.valueOf("1999-02-02 14:14:14");
			
		    Computer myComp = null;
		    System.out.println(ts_introduced+" __ "+ts_discontinued+"company id = "+request.getParameter("company")+" id2 = "+Long.parseLong(request.getParameter("company")));
			if(myCompanyDAO.existCompanyId(Long.parseLong(request.getParameter("company"))))
				myComp = new Computer(request.getParameter("name"), ts_introduced,	ts_discontinued, Long.parseLong(request.getParameter("company")));
			else
				System.out.println("Error company ID");
			myComputerDAO.addComputer(myComp);
			System.out.println("[POSTED] add mode : "+myComp);
			
		}
		else if(request.getParameter("mode").equals("edit"))
		{
			DateFormat formatter;
			Date dateFormattedIntroduced = null;
			Date dateFormattedDiscontinued = null;
			formatter = new SimpleDateFormat("YYYY-mm-dd");
			try {
				dateFormattedIntroduced = (Date)formatter.parse(request.getParameter("introduced"));
				dateFormattedDiscontinued = (Date)formatter.parse(request.getParameter("discontinued"));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			System.out.println("[POSTED] edit mode!");
			Computer myComp = new Computer(Long.parseLong(request.getParameter("id")),
					request.getParameter("name"),
					dateFormattedIntroduced,
					dateFormattedDiscontinued,
					Long.parseLong("company"));
			myComputerDAO.editComputer(myComp);
		}
		request.getRequestDispatcher("WEB-INF/dashboard.jsp?display=10").forward(request, response);
	}

}