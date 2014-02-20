package com.excilys.formation.projet.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.services.ComputerService;

public class DAOTest {
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "jee-cdb";
	private static String passwd = "password";
	static final Logger LOG = LoggerFactory.getLogger(DAOTest.class);

	public static void main(String[] args) {
		ComputerService CS = new ComputerService();
		Company cp = new Company(1, "Apple Inc.");
		Computer C = new Computer("AAATest", null, null, cp);
		CS.add(C);
	}
}
