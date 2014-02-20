package com.excilys.formation.projet.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.projet.om.Log;
import com.excilys.formation.projet.services.LogService;
import com.excilys.formation.projet.servlet.wrapper.Page;

/**
 * Servlet implementation class SelectLogsServlet
 */
@WebServlet("/SelectLogsServlet")
public class SelectLogsServlet extends HttpServlet {
	private LogService logService = new LogService();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectLogsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Page<Log> myPage = new Page<Log>();
		myPage.results = logService.readAll();
		request.setAttribute("pageData", myPage);
		request.getRequestDispatcher("WEB-INF/logs.jsp").forward(request,
				response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
