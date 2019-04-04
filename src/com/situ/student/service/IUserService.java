package com.situ.student.service;

import com.situ.student.entity.User;

public interface IUserService {

	User login(String name, String password);

}
