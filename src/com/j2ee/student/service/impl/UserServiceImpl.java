package com.j2ee.student.service.impl;

import com.j2ee.student.dao.IUserDao;
import com.j2ee.student.dao.impl.UserDaoImpl;
import com.j2ee.student.entity.User;
import com.j2ee.student.service.IUserService;

public class UserServiceImpl implements IUserService {
	private IUserDao userDao = new UserDaoImpl();

	@Override
	public User login(String name, String password) {
		return userDao.login(name, password);
	}

}
