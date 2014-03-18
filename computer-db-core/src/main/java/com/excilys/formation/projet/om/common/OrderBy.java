package com.excilys.formation.projet.om.common;

import org.springframework.data.domain.Sort;

public enum OrderBy {

	COMPUTER_NAME_ASC(new Sort(Sort.Direction.ASC, "name"), "name", "ASC"),
	COMPUTER_NAME_DESC(new Sort(Sort.Direction.DESC, "name"), "name", "DESC"),
	INTRODUCED_DATE_ASC(new Sort(Sort.Direction.ASC, "introduced"), "introduced", "ASC"),
	INTRODUCED_DATE_DESC(new Sort(Sort.Direction.DESC, "introduced"), "introduced", "DESC"),
	DISCONTINUED_DATE_ASC(new Sort(Sort.Direction.ASC, "discontinued"), "discontinued", "ASC"),
	DISCONTINUED_DATE_DESC(new Sort(Sort.Direction.DESC, "discontinued"), "discontinued", "DESC"),
	COMPANY_NAME_ASC(new Sort(Sort.Direction.ASC,"company.name"), "company", "ASC"),
	COMPANY_NAME_DESC(new Sort(	Sort.Direction.DESC, "company.name"), "company", "DESC");

	private Sort value;
	private String colName = "name";
	private String dir = "ASC";

	private OrderBy(Sort value, String colName, String dir) {
		this.setValue(value);
		this.setColName(colName);
		this.setDir(dir);
	}

	public Sort getValue() {
		return value;
	}

	private void setValue(Sort value) {
		this.value = value;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getDir() {
		return dir;
	}

	private void setDir(String dir) {
		this.dir = dir;
	}

	public static OrderBy get(String colName, String dir) {
		if (colName == null)
			return null;

		if (dir == null)
			dir = "asc";

		OrderBy[] list = OrderBy.values();

		for (OrderBy col : list) {
			if (col.getColName().equals(colName) && col.getDir().equals(dir))
				return col;
		}
		return null;
	}

	public String getDirForCol(String col) {

		if (this.getColName().equals(col) && this.getDir().equals("asc"))
			return "desc";

		return "asc";
	}

	public static OrderBy getOrderByFromSort(Sort sort) {
		if (sort == null)
			return null;

		OrderBy[] list = OrderBy.values();

		for (OrderBy ob : list) {
			if (ob.getValue().equals(sort))
				return ob;
		}
		return null;
	}

}