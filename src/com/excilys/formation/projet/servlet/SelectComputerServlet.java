package com.excilys.formation.projet.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.services.CompanyService;
import com.excilys.formation.projet.services.ComputerService;
import com.excilys.formation.projet.servlet.wrapper.Page;
import com.excilys.formation.projet.util.Validation;

/**
 * Servlet implementation class SelectComputerServlet
 */
@WebServlet("/SelectComputerServlet")
public class SelectComputerServlet extends HttpServlet {

	static final Logger LOG = LoggerFactory
			.getLogger(SelectComputerServlet.class);

	private CompanyService companyService = new CompanyService();
	private ComputerService computerService = new ComputerService();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectComputerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Http Get request catched.");

		if (request.getParameterMap().isEmpty()) {
			request.setAttribute("options", companyService.read());
			LOG.error("The computer id could not be found on request.");
			request.setAttribute("answer", 0);
			request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(
					request, response);
			return;
		} else {
			request.setAttribute("options", companyService.read());
			Computer myComp = computerService.read(Long.parseLong(request
					.getParameter("computer")));
			if (myComp == null) {
				LOG.error("The computer id could not be found on database.");
				request.setAttribute("answer", 0);
			} else
				request.setAttribute("computer", myComp);
			request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(
					request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Http Post request catched.");

		request.setAttribute("computers", computerService.readAll());

		if (request.getParameter("mode") == null
				|| request.getParameter("mode").equals("")) {
			LOG.warn("Undefined computer handle mode.");
			request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(
					request, response);
			return;
		} else if (request.getParameter("mode").equals("del")) {
			if (computerService.delete(Long.parseLong(request
					.getParameter("computer")))) {
				request.setAttribute(
						"error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"success", "Computer deleted",
								"The computer was successfully deleted from the database."));
				LOG.info("Computer successfully deleted!");
			} else {
				request.setAttribute(
						"error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"danger", "Fail to delete computer",
								"The computer was not deleted to the database please try again."));
				LOG.info("The computer can not be delete.");
			}
			Page<Computer> myPage = new Page<Computer>();
			myPage.results = computerService.readAll();
			myPage.totalNumberOfRecords = computerService.readTotal();
			request.setAttribute("pageData", myPage);
			request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(
					request, response);
			return;
		} else if (request.getParameter("mode").equals("add")) {
			Timestamp ts_introduced = null;
			Timestamp ts_discontinued = null;
			Computer myComp = null;
			boolean has_errors = false;
			StringBuilder error = new StringBuilder("(Errors : ");
			if (!Validation.validateString(request.getParameter("name"))) {
				error.append("\nComputer name failed / ");
				has_errors = true;
			}
			if (!Validation.validateString(request.getParameter("company"))) {
				error.append("\nCompany name failed / ");
				has_errors = true;
			}
			if (!Validation.validateString(request.getParameter("introduced"))) {
				error.append("\nIntroduced date failed / ");
				has_errors = true;
			}
			if (!Validation
					.validateString(request.getParameter("discontinued"))) {
				error.append("\nDiscontinued date failed");
				has_errors = true;
			}
			error.append(")");
			if (has_errors) {
				request.setAttribute("error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"danger", "Fail to add computer",
								"The computer was not added to database, please try again."
										+ error));
				request.setAttribute("mode", request.getParameter("mode"));
				request.getRequestDispatcher("WEB-INF/addComputer.jsp")
						.forward(request, response);
				LOG.info("The computer can not be add.");
				return;
			}
			if (companyService.exist(Long.parseLong(request
					.getParameter("company")))) {
				DateFormat formatter;
				Date dateFormattedIntroduced = null;
				Date dateFormattedDiscontinued = null;
				formatter = new SimpleDateFormat("yyyy-mm-dd");
				try {
					dateFormattedIntroduced = (Date) formatter.parse(request
							.getParameter("introduced"));
					dateFormattedDiscontinued = (Date) formatter.parse(request
							.getParameter("discontinued"));
				} catch (ParseException e1) {
					LOG.error("Adding computer parse exception catch.");
					e1.printStackTrace();
				}
				myComp = new Computer(request.getParameter("name"),
						dateFormattedIntroduced, dateFormattedDiscontinued,
						new Company(Long.parseLong(request
								.getParameter("company"))));

			}

			if (computerService.add(myComp) > 0) {
				request.setAttribute(
						"error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"success", "Computer added",
								"Congratulations, the computer has been added to the database."));
				Page<Computer> myPage = new Page<Computer>();
				myPage.results = computerService.readAll();
				myPage.totalNumberOfRecords = computerService.readTotal();
				request.setAttribute("pageData", myPage);
				request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(
						request, response);
				return;
			} else {
				request.setAttribute(
						"error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"danger", "Fail to add computer",
								"The computer was not added to the database please try again."));
				request.setAttribute("mode", request.getParameter("mode"));
				request.getRequestDispatcher("WEB-INF/addComputer.jsp")
						.forward(request, response);
				LOG.info("The computer can not be add.");
				return;
			}
		} else if (request.getParameter("mode").equals("edit")) {
			boolean has_errors = false;
			StringBuilder error_params = new StringBuilder("(Errors : ");

			if (!Validation.validateString(request.getParameter("name"))) {
				error_params.append("\nComputer name failed / ");
				has_errors = true;
			}
			if (!Validation.validateString(request.getParameter("company"))) {
				error_params.append("\nCompany name failed / ");
				has_errors = true;
			}
			if (!Validation.validateString(request.getParameter("introduced"))) {
				error_params.append("\nIntroduced date failed / ");
				has_errors = true;
			}
			if (!Validation
					.validateString(request.getParameter("discontinued"))) {
				error_params.append("\nDiscontinued date failed");
				has_errors = true;
			}
			error_params.append(")");
			if (has_errors) {
				request.setAttribute("error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"danger", "Fail to edit computer",
								"The computer was not edited please try again."
										+ error_params));
				LOG.info("Fail to access edit computer.");
				request.getRequestDispatcher(
						"WEB-INF/addComputer.jsp?computer="
								+ request.getParameter("comp_id")).forward(
						request, response);
				return;
			}

			DateFormat formatter;
			Date dateFormattedIntroduced = null;
			Date dateFormattedDiscontinued = null;
			formatter = new SimpleDateFormat("yyyy-mm-dd");
			try {
				dateFormattedIntroduced = (Date) formatter.parse(request
						.getParameter("introduced"));
				dateFormattedDiscontinued = (Date) formatter.parse(request
						.getParameter("discontinued"));
			} catch (ParseException e1) {
				request.setAttribute(
						"error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"danger", "Fail to edit computer",
								"The computer was not updated, please try again."));
				request.getRequestDispatcher(
						"WEB-INF/addComputer.jsp?computer="
								+ request.getParameter("comp_id")).forward(
						request, response);
				LOG.error("Editing computer parse exception catch.");
				e1.printStackTrace();
				return;
			}

			Computer myComp = new Computer(Long.parseLong(request
					.getParameter("comp_id")), request.getParameter("name"),
					dateFormattedIntroduced, dateFormattedDiscontinued,
					companyService.read(Long.parseLong(request
							.getParameter("company"))));

			if (computerService.edit(myComp)) {
				request.setAttribute(
						"error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"success", "Computer edited",
								"Congratulations, the computer has been edited and data overwritten."));
				LOG.info("Computer successfully edited.");
				Page<Computer> myPage = new Page<Computer>();
				myPage.results = computerService.readAll();
				myPage.totalNumberOfRecords = computerService.readTotal();
				request.setAttribute("pageData", myPage);
				request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(
						request, response);
				return;
			} else {
				request.setAttribute(
						"error",
						new com.excilys.formation.projet.servlet.wrapper.Error(
								"danger", "Fail to edit computer",
								"The computer was not updated, please try again."));
				LOG.info("Fail to edit computer.");
				request.getRequestDispatcher(
						"WEB-INF/addComputer.jsp?computer="
								+ request.getParameter("comp_id")).forward(
						request, response);
				return;
			}
		}

	}
}
