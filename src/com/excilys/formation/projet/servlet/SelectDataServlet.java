package com.excilys.formation.projet.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.projet.DAO.CompanyDAO;
import com.excilys.formation.projet.DAO.ComputerDAO;
import com.excilys.formation.projet.DAO.DAOFactory;
import com.excilys.formation.projet.OM.Computer;

/**
 * Servlet implementation class SelectDataServlet
 */
@WebServlet("/SelectDataServlet")
public class SelectDataServlet extends HttpServlet {
	private ComputerDAO myComputerDAO;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectDataServlet() {
        super();
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOFactory.getInstance();
		myComputerDAO = DAOFactory.getMyComputerDAO();
		
		if(request.getParameterMap().isEmpty())
		{
			request.setAttribute("computers", myComputerDAO.readAll());
			request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
			return;
		}		
		int page = 1;
		int limit = 20;
		int offset = 0;
		String searchComputer = "";
		String searchCompany = "";
		String sortResult = "";
		
		if(request.getParameter("page")==null || "".equals(request.getParameter("page")))
			page = 1;
		else{
			page = Integer.parseInt(request.getParameter("page"));
			offset = (page-1)*limit;
		}
			
		if(request.getParameter("display")==null || "".equals(request.getParameter("display"))){
			limit = 20;
		}
		else{
			System.out.println(request.getParameter("display"));
			if(!request.getParameter("display").matches("\\d+") || Integer.parseInt(request.getParameter("display"))<=0)
				limit = Integer.parseInt(request.getParameter("display"));
			else
				limit = 20;
		}
		
		if(request.getParameter("search")==null || "".equals(request.getParameter("search")))
			searchComputer = "";
		else
			searchComputer = request.getParameter("search");
		
		if(request.getParameter("company")==null || "".equals(request.getParameter("company")))
			searchCompany = "";
		else
			searchCompany = request.getParameter("company");
		
		if(request.getParameter("sort")==null || "".equals(request.getParameter("sort")))
			sortResult = "";
		else
		{
			if("computer".equals(request.getParameter("sort")))
				sortResult = "computer ";
			else if("company".equals(request.getParameter("sort")))
				sortResult = "company";
			else if("introduced".equals(request.getParameter("sort")))
				sortResult = "introduced";
			else if("discontinued".equals(request.getParameter("sort")))
				sortResult = "discontinued";
		}
		
		request.setAttribute("currentPage", page);
		System.out.println(offset+"/"+limit);
		request.setAttribute("computers", myComputerDAO.readRanged(offset, limit));
		
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
		
		return;
		
		//request.setAttribute("answer", 0);
		//request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
		/*
		if("".equals(request.getParameter("display")) || !request.getParameter("display").matches("\\d+") || Integer.parseInt(request.getParameter("display"))<=0)
		{
			if("".equals(request.getParameter("search")))
			{
				myComputerDAO.clearMyComputers();
				myComputerDAO.getComputers(toDisplay);
				request.setAttribute("computers", myComputerDAO.getMyComputers());
			}
			else
			{
				myComputerDAO.clearMyComputers();
				myComputerDAO.getComputersByName(toDisplay, request.getParameter("search"));
				request.setAttribute("computers", myComputerDAO.getMyComputers());
			}
		}
		else
		{
			toDisplay = Integer.parseInt(request.getParameter("display"));
			if(request.getParameter("search") == null || request.getParameter("search").equals("")){
				myComputerDAO.clearMyComputers();
				myComputerDAO.getComputers(toDisplay);
				request.setAttribute("computers", myComputerDAO.getMyComputers());
			}
			else
			{
				myComputerDAO.clearMyComputers();
				myComputerDAO.getComputersByName(toDisplay, request.getParameter("search"));
				request.setAttribute("computers", myComputerDAO.getMyComputers());
			}/SelectDataServlet
			
		}	
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
		*/
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
