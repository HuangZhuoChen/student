package com.j2ee.student.listener;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.j2ee.student.entity.User;

public class MyHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("MyHttpSessionListener.sessionDestroyed()");
		HttpSession session = se.getSession();
		User user = (User) session.getAttribute("user");

		// 从ServletContext中取出在线列表集合
		List<User> list = (List<User>) session.getServletContext()
				.getAttribute("onLineUserList");
		list.remove(user);
	}
}
