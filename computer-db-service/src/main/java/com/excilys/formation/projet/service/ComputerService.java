package com.excilys.formation.projet.service;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.projet.om.domain.Computer;
import com.excilys.formation.projet.om.domain.Log;
import com.excilys.formation.projet.repositories.ComputerRepository;
import com.excilys.formation.projet.repositories.LogRepository;


@Service("computerService")
@Transactional
public class ComputerService{

	static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

	@Autowired
	ComputerRepository computerRepository;

	@Autowired
	LogRepository logRepository;

	public ComputerService() {
		super();
	}

	@Transactional(readOnly = true)
	public long readTotal(String search) {
		return computerRepository.count();
	}

	@Transactional(readOnly = true)
	public Computer read(long id) {
		return computerRepository.findOne(id);
	}

	public void delete(long id) {
		computerRepository.delete(id);
		logRepository.save(new Log("DELETE", new DateTime(), "id = " + id));
	}

	@Transactional(readOnly = true)
	public List<Computer> read(int min, int max, String type, String field,
			String search) {
		return (List<Computer>) computerRepository.findAll();
	}
	
	public long create(Computer myComp) {
		computerRepository.save(myComp);
		logRepository.save(new Log("CREATE", new DateTime(), "id = "
				+ myComp.getId()));
		return myComp.getId();
	}

	public void update(Computer myComp) {
		computerRepository.save(myComp);
		logRepository.save(new Log("UPDATE", new DateTime(), "id = "
				+ myComp.getId()));
	}

	public Page<Computer> retrievePage(Pageable pageable, String search) {
		Page<Computer> page = null;

		if (search == null){
			page = computerRepository.findAll(pageable);
		}
		else{
			page = computerRepository.findAll(new StringBuilder("%").append(search.toLowerCase()).append("%").toString(), pageable);
		}
		return page;
	}
}
