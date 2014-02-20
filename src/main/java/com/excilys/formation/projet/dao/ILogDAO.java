package com.excilys.formation.projet.dao;

import java.util.List;

import com.excilys.formation.projet.om.Log;

public interface ILogDAO {

	public abstract List<Log> readAll();

	public abstract boolean create(Log log);

}