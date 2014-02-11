package com.excilys.formation.projet.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {
	public static Date dateFromTimestamp(Timestamp date){
		String str_date = date.toString();
		DateFormat formatter; 
		Date dateFormatted = null; 
		formatter = new SimpleDateFormat("YYYY-mm-dd");
		try {
			dateFormatted = (Date)formatter.parse(str_date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return new Date(dateFormatted.getTime());
	}
	public static Timestamp timestampFromDate(Date date){
		String str_date = date.toString();
		DateFormat formatter; 
		Date dateFormatted = null; 
		formatter = new SimpleDateFormat("YYYY-mm-dd");
		try {
			dateFormatted = (Date)formatter.parse(str_date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		} 
		return new Timestamp(dateFormatted.getTime());
	}
}
