package com.excilys.formation.projet.dao;

import java.util.List;

import com.excilys.formation.projet.om.Computer;

public interface IComputerDAO {

	public abstract int readTotal();

	public abstract int readTotal(String search);

	public abstract Computer read(long id);

	public abstract boolean exist(long id);

	public abstract boolean delete(long id);

	public abstract List<Computer> read(int min, int max, String type,
			String field, String search);

	public abstract List<Computer> readAll();

	public abstract long add(Computer myComp);

	public abstract boolean edit(Computer myComp);

	List<Computer> readRangedOrdered(int min, int max, String type, String field);

}