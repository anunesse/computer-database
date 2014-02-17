package com.excilys.formation.projet.dao;

import java.util.List;

import com.excilys.formation.projet.om.Computer;

public interface IComputerDAO {

	public abstract int readTotal();

	public abstract Computer read(long id);

	public abstract boolean exist(long id);

	public abstract boolean delete(long id);

	public abstract List<Computer> readName(int max, String name);

	public abstract List<Computer> readRanged(int min, int max);

	public abstract List<Computer> readRangedOrderedSearchComputer(int min,
			int max, boolean type, String field, String search);

	public abstract List<Computer> readAll();

	public abstract boolean add(Computer myComp);

	public abstract boolean edit(Computer myComp);

	List<Computer> readRangedOrdered(int min, int max, String type, String field);

}