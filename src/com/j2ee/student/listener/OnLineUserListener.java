package com.j2ee.student.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.j2ee.student.entity.User;

public class OnLineUserListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//将初始化好空的在线列表集合List<User> onLineUserList，
		List<User> onLineUserList = new ArrayList<User>();
		//放到ServletContext域对象中。
		ServletContext servletContext = sce.getServletContext();
		servletContext.setAttribute("onLineUserList", onLineUserList);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
