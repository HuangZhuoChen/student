package com.j2ee.student.service;

import com.j2ee.student.entity.User;

public interface IUserService {

	User login(String name, String password);

}
