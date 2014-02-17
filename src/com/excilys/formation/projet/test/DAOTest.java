package com.excilys.formation.projet.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOTest {

	static final Logger LOG = LoggerFactory.getLogger(DAOTest.class);

	public static void main(String[] args) {
		/*
		 * Timestamp myTimestamp = new Timestamp(99,02,14,15,30,25,20); Date
		 * myDate = new Date(11,11,15,22,23,48);
		 * 
		 * Date myDate2 = new Date(99,02,14,15,30,25); Timestamp myTimestamp2 =
		 * Converter.timestampFromDate(myDate2);
		 * 
		 * System.out.println("TS 1 = "+myTimestamp.toLocaleString());
		 * System.out.println("TS 1 = "+myTimestamp.getTime());
		 * 
		 * System.out.println("DATE 2 = "+myDate2.getTime());
		 * 
		 * System.out.println("DATE 1 = "+myDate.toLocaleString());
		 * 
		 * 
		 * System.out.println("DATE 2 = "+myDate2);
		 * 
		 * System.out.println("TS 2 = "+myTimestamp2.toLocaleString());
		 * System.out.println("TS 2 = "+myTimestamp2.getTime());
		 */

		// String
		// myStr="MacBook 13-inch Core 2 Duo 2.13GHz (MC240LL/A) DDR2 Model";
		// System.out.println(Validation.validateString(myStr));

		/*
		 * DAOFactory.getInstance(); IComputerDAO myComputerDAO =
		 * DAOFactory.getMyComputerDAO();
		 */
		LOG.trace("Hello World!");
		LOG.info("I am fine.");
		LOG.warn("I love programming.");
		LOG.error("I am programming.");
	}

}
