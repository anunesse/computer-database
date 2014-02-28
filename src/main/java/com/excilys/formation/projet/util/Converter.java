package com.excilys.formation.projet.util;

import java.sql.Timestamp;

import org.joda.time.DateTime;

public class Converter {
	public static DateTime dateFromTimestamp(Timestamp date){
		return new DateTime(date);
	}
	public static Timestamp timestampFromDate(DateTime date){
		return new Timestamp(date.getMillis());
	}
}
