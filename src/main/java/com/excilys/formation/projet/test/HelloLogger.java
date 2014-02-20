package com.excilys.formation.projet.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelloLogger {

	public static void main(String[] args) {
		Logger myLogger = LoggerFactory.getLogger(HelloLogger.class);
		myLogger.info("Hello my Logger!");
	}

}
