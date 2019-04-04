package com.j2ee.student.dao.impl;


import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.j2ee.student.dao.IUserDao;
import com.j2ee.student.entity.User;
import com.j2ee.student.util.C3P0Util;

public class UserDaoImpl implements IUserDao {
	private QueryRunner queryRunner = new QueryRunner(C3P0Util.getDataSource());

	@Override
	public User login(String name, String password) {
		String sql = "select id,name,password from user "
				+ "where name=? and password=?";
		Object[] params = {name, password};
		User user = null;
		try {
			user = queryRunner.query(sql, new BeanHandler<User>(User.class), params);
			queryRunner.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
