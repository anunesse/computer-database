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
import com.excilys.formation.projet.om.Computer;
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
		Page<Computer> myPage = new Page<Computer>();

		if (request.getParameterMap().isEmpty()) {
			myPage.results = myComputerDAO.readAll();
			myPage.totalNumberOfRecords = myComputerDAO.readTotal();
			request.setAttribute("pageData", myPage);
			request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(
					request, response);
			return;
		}

		int offset = 0;
		int page = 1;
		int limit = 20;
		if (request.getParameter("display") == null
				|| "".equals(request.getParameter("display"))) {
			limit = 20;
		} else {
			System.out.println(request.getParameter("display"));
			if (!request.getParameter("display").matches("\\d+")
					|| Integer.parseInt(request.getParameter("display")) <= 0)
				limit = 20;
			else
				limit = Integer.parseInt(request.getParameter("display"));
		}
		myPage.recordsOnThisPage = limit;

		if (request.getParameter("page") == null
				|| "".equals(request.getParameter("page")))
			page = 1;
		else {
			page = Integer.parseInt(request.getParameter("page"));
			offset = Math.max((page - 1) * limit, 0);
		}
		myPage.pageNumber = page;

		String search = "";
		String sortResult = "c.id";
		if (request.getParameter("search") == null
				|| "".equals(request.getParameter("search"))) {
			search = "";
			myPage.totalNumberOfRecords = myComputerDAO.readTotal();
		} else {
			search = request.getParameter("search");
			myPage.totalNumberOfRecords = myComputerDAO.readTotal(search);
		}

		String sortMode = "ASC";
		if (request.getParameter("order") == null
				|| "".equals(request.getParameter("order"))) {
			sortMode = "ASC";
		} else {
			sortMode = request.getParameter("order");
		}

		if (request.getParameter("orderField") == null
				|| "".equals(request.getParameter("orderField"))) {
			sortResult = "c.id";
			myPage.resultsOrderedBy = "id";
		} else {
			if ("computer".equals(request.getParameter("orderField"))) {
				sortResult = "c.name";
				myPage.resultsOrderedBy = "computer";
			} else if ("company".equals(request.getParameter("orderField"))) {
				sortResult = "b.name";
				myPage.resultsOrderedBy = "company";
			} else if ("introduced".equals(request.getParameter("orderField"))) {
				sortResult = "c.introduced";
				myPage.resultsOrderedBy = "introduced";
			} else if ("discontinued"
					.equals(request.getParameter("orderField"))) {
				sortResult = "c.discontinued";
				myPage.resultsOrderedBy = "discontinued";
			} else {
				sortResult = "c.id";
				myPage.resultsOrderedBy = "id";
			}
		}

		myPage.orderDirection = sortMode;
		myPage.pageNumber = page;

		request.setAttribute("search", search);
		LOG.debug(sortResult + "/" + sortMode);
		myPage.results = myComputerDAO.read(limit, offset, sortResult,
				sortMode, search);
		// myComputerDAO.re
		myPage.numberOfPages = (int) Math.ceil(myPage.totalNumberOfRecords
				/ myPage.recordsOnThisPage) + 1;
		// LOG.debug(myPage.toString());
		request.setAttribute("pageData", myPage);

		// System.out.println(myPage);
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request,
				response);
		return;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
