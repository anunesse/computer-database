package com.excilys.formation.projet.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.dao.IComputerDAO;
import com.excilys.formation.projet.servlet.wrapper.Page;

/**
 * Servlet implementation class SelectDataServlet
 */
@WebServlet("/SelectDataServlet")
public class SelectDataServlet extends HttpServlet {
	private IComputerDAO myComputerDAO;
	static final Logger LOG = LoggerFactory.getLogger(SelectDataServlet.class);

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectDataServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DAOFactory.getInstance();
		myComputerDAO = DAOFactory.getMyComputerDAO();
		Page myPage = new Page();

		if (request.getParameterMap().isEmpty()) {
			myPage.results = myComputerDAO.readAll();
			myPage.totalNumberOfRecords = myComputerDAO.readTotal();
			myPage.pageNumber = 1;
			request.setAttribute("pageData", myPage);
			request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(
					request, response);
			return;
		}

		int page = 1;
		int limit = 20;
		int offset = 0;
		String searchComputer = "";
		String searchCompany = "";
		String sortResult = "";
		String orderDir = "DESC";

		if (request.getParameter("pageNumber") == null
				|| "".equals(request.getParameter("pageNumber")))
			page = 1;
		else {
			page = Integer.parseInt(request.getParameter("pageNumber"));
			offset = (page - 1) * limit;
		}

		if (request.getParameter("recordsOnThisPage") == null
				|| "".equals(request.getParameter("recordsOnThisPage"))) {
			limit = 20;
		} else {
			System.out.println(request.getParameter("recordsOnThisPage"));
			if (!request.getParameter("recordsOnThisPage").matches("\\d+")
					|| Integer.parseInt(request
							.getParameter("recordsOnThisPage")) <= 0)
				limit = Integer.parseInt(request
						.getParameter("recordsOnThisPage"));
			else
				limit = 20;
		}
		/*
		 * resultsOrderedBy orderDirection
		 */
		if (request.getParameter("orderDirection") == null
				|| "".equals(request.getParameter("orderDirection")))
			orderDir = "DESC";
		else
			orderDir = request.getParameter("orderDirection");

		if (request.getParameter("search") == null
				|| "".equals(request.getParameter("search")))
			searchComputer = "";
		else
			searchComputer = request.getParameter("search");

		if (request.getParameter("company") == null
				|| "".equals(request.getParameter("company")))
			searchCompany = "";
		else
			searchCompany = request.getParameter("company");

		if (request.getParameter("resultsOrderedBy") == null
				|| "".equals(request.getParameter("resultsOrderedBy")))
			sortResult = "";
		else {
			if ("computer".equals(request.getParameter("resultsOrderedBy")))
				sortResult = "c.name";
			else if ("company".equals(request.getParameter("resultsOrderedBy")))
				sortResult = "b.name";
			else if ("introduced".equals(request
					.getParameter("resultsOrderedBy")))
				sortResult = "c.introduced";
			else if ("discontinued".equals(request
					.getParameter("resultsOrderedBy")))
				sortResult = "c.discontinued";
		}

		System.out.println(offset + "/" + limit);

		myPage.pageNumber = page;
		myPage.resultsOrderedBy = sortResult;
		myPage.orderDirection = orderDir;
		myPage.results = myComputerDAO.readRangedOrdered(limit, offset,
				orderDir, sortResult);
		request.setAttribute("pageData", myPage);
		LOG.debug(myPage.toString());
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request,
				response);
		return;

		// request.setAttribute("answer", 0);
		// request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request,
		// response);
		/*
		 * if("".equals(request.getParameter("display")) ||
		 * !request.getParameter("display").matches("\\d+") ||
		 * Integer.parseInt(request.getParameter("display"))<=0) {
		 * if("".equals(request.getParameter("search"))) {
		 * myComputerDAO.clearMyComputers();
		 * myComputerDAO.getComputers(toDisplay);
		 * request.setAttribute("computers", myComputerDAO.getMyComputers()); }
		 * else { myComputerDAO.clearMyComputers();
		 * myComputerDAO.getComputersByName(toDisplay,
		 * request.getParameter("search")); request.setAttribute("computers",
		 * myComputerDAO.getMyComputers()); } } else { toDisplay =
		 * Integer.parseInt(request.getParameter("display"));
		 * if(request.getParameter("search") == null ||
		 * request.getParameter("search").equals("")){
		 * myComputerDAO.clearMyComputers();
		 * myComputerDAO.getComputers(toDisplay);
		 * request.setAttribute("computers", myComputerDAO.getMyComputers()); }
		 * else { myComputerDAO.clearMyComputers();
		 * myComputerDAO.getComputersByName(toDisplay,
		 * request.getParameter("search")); request.setAttribute("computers",
		 * myComputerDAO.getMyComputers()); }/SelectDataServlet
		 * 
		 * }
		 * request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request
		 * , response);
		 */
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
