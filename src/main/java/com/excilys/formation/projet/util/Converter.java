package com.excilys.formation.projet.util;

import java.sql.Timestamp;
import java.util.Date;

public class Converter {
	public static Date dateFromTimestamp(Timestamp date){
		return new Date(date.getTime());
	}
	public static Timestamp timestampFromDate(Date date){
		return new Timestamp(date.getTime());
	}
}
