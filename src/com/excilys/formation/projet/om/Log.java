package com.excilys.formation.projet.om;

import java.util.Date;

public class Log {

	private long id;
	private Enum operationType;
	private Date operationDate;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Enum getOperationType() {
		return operationType;
	}

	public void setOperationType(Enum operationType) {
		this.operationType = operationType;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
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

	public Log(long id, Enum operationType, Date operationDate,
			String description) {
		super();
		this.id = id;
		this.operationType = operationType;
		this.operationDate = operationDate;
		this.description = description;
	}

}
