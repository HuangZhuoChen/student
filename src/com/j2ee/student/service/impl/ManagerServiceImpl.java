package com.j2ee.student.service.impl;

import java.util.List;
import java.util.Map;

import com.j2ee.student.dao.IManagerDao;
import com.j2ee.student.dao.impl.ManagerDaoImpl;
import com.j2ee.student.service.IManagerService;

public class ManagerServiceImpl implements IManagerService {
	private IManagerDao managerDao = new ManagerDaoImpl();
	
	@Override
	public List<Map<String, Object>> findAll() {
		return managerDao.findAllByJdbc();
	}

}
