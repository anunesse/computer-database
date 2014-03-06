package com.excilys.formation.projet.dao;

import java.util.List;

import com.excilys.formation.projet.om.Computer;

public interface IComputerDAO {

	public abstract int readTotal();

	public abstract int readTotal(String search);

	public abstract Computer read(long id);

	public abstract void delete(long id);

	public abstract List<Computer> read(int min, int max, String type,
			String field, String search);

	public abstract List<Computer> readAll();

	public abstract long create(Computer myComp);

	public abstract void update(Computer myComp);

}