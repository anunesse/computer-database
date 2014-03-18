package com.excilys.formation.projet.om.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name="log")
public class Log {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="optype")
	private String operationType;
	
	@Column(name="opdate")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime operationDate;
	
	@Column(name="description")
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public DateTime getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(DateTime operationDate) {
		this.operationDate = operationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Log() {
		super();
	}

	public Log(long id, String operationType, DateTime operationDate,
			String description) {
		super();
		this.id = id;
		this.operationType = operationType;
		this.operationDate = operationDate;
		this.description = description;
	}

	public Log(String operationType, DateTime operationDate, String description) {
		super();
		this.operationType = operationType;
		this.operationDate = operationDate;
		this.description = description;
	}

}