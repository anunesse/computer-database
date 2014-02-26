package com.excilys.formation.projet.om;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class Computer {
	private long id;
	@NotEmpty
	private String name;
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date introduced;
	@DateTimeFormat(pattern="yyyy-mm-dd")
	private Date discontinued;
	private Company company;
	
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
	/*public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}*/
	public Computer() {
		super();
	}
	
	public Computer build(){
		return this;
	}
	
	/*public Computer(long id, String name, Timestamp introduced,
			Timestamp discontinued, long company_id) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}*/
	public Computer(String name, Date Introduced,
			Date Discontinued, Company company) {
		this.name = name;
		this.introduced = Introduced;
		this.discontinued = Discontinued;
		this.company = company;
	}
	public Computer(String name, Date introduced, Date discontinued) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		//this.company_id = company_id;
	}
	public Computer(long id, String name, Date introduced, Date discontinued) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		//this.company_id = company_id;
	}
	public Computer(long id, String name, Date date,
			Date date2, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = date;
		this.discontinued = date2;
		this.company = company;
	}
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued
				+ "]";
	}
	
}
