package com.excilys.formation.projet.om;

import org.joda.time.DateTime;


public class Log {

	private long id;
	private String operationType;
	private DateTime operationDate;
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