package com.excilys.formation.projet.OM;

import java.sql.Timestamp;
import java.util.Date;

public class Computer {
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private long company_id;
	private Company company;
	
	public Computer(String name, Date introduced, Date discontinued,
			long company_id) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	public Computer(long id, String name, Date introduced, Date discontinued,
			long company_id) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	public Computer(long id, String name, Date date,
			Date date2, long company_id, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = date;
		this.discontinued = date2;
		this.company_id = company_id;
		this.company = company;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Computer() {
		super();
	}
	public Computer(long id, String name, Timestamp introduced,
			Timestamp discontinued, long company_id) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued
				+ ", company_id=" + company_id + "]";
	}
	
}
