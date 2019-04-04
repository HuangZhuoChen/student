package com.situ.student.dao;

import com.situ.student.entity.User;

public interface IUserDao {

	User login(String name, String password);

}
