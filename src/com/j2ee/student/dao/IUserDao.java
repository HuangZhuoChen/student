package com.j2ee.student.dao;

import com.j2ee.student.entity.User;

public interface IUserDao {

	User login(String name, String password);

}
