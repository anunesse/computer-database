package com.excilys.formation.projet.om;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name="computer")
public class Computer {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="introduced")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime introduced;
	
	@Column(name="discontinued")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime discontinued;
	
	@JoinColumn(name="company_id")
	@ManyToOne(targetEntity=com.excilys.formation.projet.om.Company.class)
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
	public DateTime getIntroduced() {
		return introduced;
	}
	public DateTime getDiscontinued() {
		return discontinued;
	}
	public void setIntroduced(DateTime introduced) {
		this.introduced = introduced;
	}
	public void setDiscontinued(DateTime discontinued) {
		this.discontinued = discontinued;
	}
	
	public Computer() {
		super();
	}
	
	public Computer build(){
		return this;
	}
	
	public Computer(long id) {
		super();
		this.id = id;
	}

	public Computer(String name, DateTime Introduced,
			DateTime Discontinued, Company company) {
		this.name = name;
		this.introduced = Introduced;
		this.discontinued = Discontinued;
		this.company = company;
	}
	public Computer(String name, DateTime introduced, DateTime discontinued) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		//this.company_id = company_id;
	}
	public Computer(long id, String name, DateTime introduced, DateTime discontinued) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}
	public Computer(long id, String name, DateTime date,
			DateTime date2, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = date;
		this.discontinued = date2;
		this.company = company;
	}
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued
				+ "]";
	}
	
}
