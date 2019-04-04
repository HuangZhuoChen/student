package com.situ.student.service.impl;

import java.util.List;
import java.util.Map;

import com.situ.student.dao.IManagerDao;
import com.situ.student.dao.impl.ManagerDaoImpl;
import com.situ.student.service.IManagerService;

public class ManagerServiceImpl implements IManagerService {
	private IManagerDao managerDao = new ManagerDaoImpl();
	
	@Override
	public List<Map<String, Object>> findAll() {
		return managerDao.findAllByJdbc();
	}

}
