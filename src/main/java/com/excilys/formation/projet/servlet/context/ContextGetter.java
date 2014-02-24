package com.excilys.formation.projet.servlet.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextGetter {
	private static ContextGetter contextGetter;
	private static ApplicationContext applicationContext;

	private ContextGetter() {}

	static {
		applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	public static ContextGetter getInstance() {
		if (contextGetter == null)
			contextGetter = new ContextGetter();
		return contextGetter;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
