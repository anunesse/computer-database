package com.excilys.formation.projet.util;

import java.util.Date;

public class Validation {
	public static boolean validateString(String str){
		if("".equals(str))
			return false;
		else if(str.trim().equals(""))
			return false;
		else if(!str.matches("^[^<>'\"/;`%]*$"))
			return false;
		else
			return true;
	}
	public static boolean validateDate(Date date){
		if(date==null)
			return false;
		//Maybe add some other validation
		return true;
	}
}
