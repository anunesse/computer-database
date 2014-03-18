package com.excilys.formation.projet.dto;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.formation.projet.om.domain.Company;
import com.excilys.formation.projet.om.domain.Computer;

public class Converter {
	public static Computer fromDTO(ComputerDTO computerDTO){
		Locale currentLocale = LocaleContextHolder.getLocale();
		String pattern = DateTimeFormat.patternForStyle("S-", currentLocale);
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		
		Computer computer = new Computer();
		computer.setName(computerDTO.getName());
		if(computerDTO.getCompany() != 0){
			computer.setCompany(new Company(computerDTO.getCompany()));
		}
		else
			computer.setCompany(null);
		DateTime introduced = null;
		DateTime discontinued = null;
		
		if(!"".equals(computerDTO.getIntroduced())){
			introduced = formatter.parseDateTime(computerDTO.getIntroduced());
		}else{
			introduced = null;
		}
		if(!"".equals(computerDTO.getDiscontinued())){
			discontinued = formatter.parseDateTime(computerDTO.getDiscontinued());
		}else{
			discontinued = null;
		}
		
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		return computer;
	}
	
	public static ComputerDTO toDTO(Computer computer){
		String introduced = "";
		String discontinued = "";
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		String pattern = DateTimeFormat.patternForStyle("S-", currentLocale);
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		
		if(computer.getIntroduced()!=null){
			introduced = formatter.print(computer.getIntroduced().getMillis());
		}
		if(computer.getDiscontinued()!=null){
			discontinued = formatter.print(computer.getDiscontinued().getMillis());
		}
		
		ComputerDTO computerDTO = new ComputerDTO();
		
		computerDTO.setId(computer.getId());
		computerDTO.setName(computer.getName());
		computerDTO.setIntroduced(introduced);
		computerDTO.setDiscontinued(discontinued);
		if(computer.getCompany()!=null){
			computerDTO.setCompany(computer.getCompany().getId());
			computerDTO.setCompanyName(computer.getCompany().getName());
		}
		else{
			computerDTO.setCompany(0);
			computerDTO.setCompanyName("Unknown");
		}		
		return computerDTO;
	}
}
