package com.situ.student.service.impl;

import com.situ.student.dao.IUserDao;
import com.situ.student.dao.impl.UserDaoImpl;
import com.situ.student.entity.User;
import com.situ.student.service.IUserService;

public class UserServiceImpl implements IUserService {
	private IUserDao userDao = new UserDaoImpl();

	@Override
	public User login(String name, String password) {
		return userDao.login(name, password);
	}

}
