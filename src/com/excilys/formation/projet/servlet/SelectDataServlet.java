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
		int toDisplay = 10;
		if(request.getParameter("display")==null
				|| request.getParameter("display").equals("")
				|| !request.getParameter("display").matches("\\d+")
				|| Integer.parseInt(request.getParameter("display"))<=0){
			if(request.getParameter("search")==null || request.getParameter("search").equals(""))
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
			}
			
		}	
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
