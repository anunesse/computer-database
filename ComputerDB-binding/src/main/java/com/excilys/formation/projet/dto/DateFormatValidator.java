package com.excilys.formation.projet.dto;

import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

	static final Logger LOG = LoggerFactory.getLogger(DateFormatValidator.class);
	
	public void initialize(DateFormat arg0) {}

	public boolean isValid(String myDate, ConstraintValidatorContext arg1) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String pattern = DateTimeFormat.patternForStyle("S-", currentLocale);
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		boolean dateError = false;
		if(!"".equals(myDate)) {
			try {
				@SuppressWarnings("unused")
				DateTime date = formatter.parseDateTime(myDate);
				return true;
			} catch (IllegalArgumentException e) {
				LOG.error(e.getMessage());
			}
		}
		else if("".equals(myDate)){
			return true;
		}
		return dateError;
	}

}