package com.excilys.formation.projet.test;

import java.sql.Timestamp;
import java.util.Date;
import com.excilys.formation.projet.util.*;
import com.excilys.formation.projet.DAO.ComputerDAO;
import com.excilys.formation.projet.OM.Computer;
import com.excilys.formation.projet.util.Converter;

public class DAOTest {

	public static void main(String[] args) {
		/*Timestamp myTimestamp = new Timestamp(99,02,14,15,30,25,20);
		Date myDate = new Date(11,11,15,22,23,48);
		
		Date myDate2 = new Date(99,02,14,15,30,25);
		Timestamp myTimestamp2 = Converter.timestampFromDate(myDate2);
		
		System.out.println("TS 1 = "+myTimestamp.toLocaleString());
		System.out.println("TS 1 = "+myTimestamp.getTime());
		
		System.out.println("DATE 2 = "+myDate2.getTime());
		
		System.out.println("DATE 1 = "+myDate.toLocaleString());
		
		
		System.out.println("DATE 2 = "+myDate2);
		
		System.out.println("TS 2 = "+myTimestamp2.toLocaleString());
		System.out.println("TS 2 = "+myTimestamp2.getTime());*/
		
		String myStr="MacBook 13-inch Core 2 Duo 2.13GHz (MC240LL/A) DDR2 Model";
		System.out.println(Validation.validateString(myStr));
	}

}
