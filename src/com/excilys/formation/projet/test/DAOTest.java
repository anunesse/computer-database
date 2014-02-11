package com.excilys.formation.projet.test;

import com.excilys.formation.projet.DAO.ComputerDAO;
import com.excilys.formation.projet.OM.Computer;

public class DAOTest {

	public static void main(String[] args) {
		ComputerDAO computers_dao = new ComputerDAO();
		computers_dao.getComputers(10);
		for(Computer myC : computers_dao.getMyComputers())
			System.out.println(myC);
		computers_dao.CloseConnection();
	}

}
