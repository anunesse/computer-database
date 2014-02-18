package com.excilys.formation.projet.dao;

import java.util.List;

import com.excilys.formation.projet.om.Company;

public interface ICompanyDAO {

	public abstract Company read(long id);

	public abstract List<Company> read(int max);

	public abstract List<Company> read();

	public abstract boolean exist(long id);

}