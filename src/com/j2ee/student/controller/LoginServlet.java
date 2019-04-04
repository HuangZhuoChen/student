package com.situ.student.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.situ.student.entity.User;
import com.situ.student.service.IUserService;
import com.situ.student.service.impl.UserServiceImpl;

/**
 * Servlet implementation class LoginServlet2
 */
public class LoginServlet extends BaseServlet {
	private IUserService userService = new UserServiceImpl();
	
	public void getLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/user_login.jsp").forward(request, response);
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		 
		String checkCode = request.getParameter("checkCode");
		String checkCodeSession = (String) request.getSession().getAttribute("checkCodeSession");
		if (checkCode == null 
				|| "".equals(checkCode)
				|| !checkCode.equalsIgnoreCase(checkCodeSession)) {
			response.sendRedirect(request.getContextPath() + "/student?method=searchByCondition");
			return;
		}
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		User user = userService.login(name, password);
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			//浠嶴ervletContext涓彇鍑哄湪绾垮垪琛ㄩ泦鍚�
			List<User> list = (List<User>) getServletContext()
					.getAttribute("onLineUserList");
			list.add(user);
			//getServletContext().setAttribute("onLineUserList", list);
			
			response.sendRedirect(request.getContextPath() + "/student?method=searchByCondition");
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/user?method=getLoginPage");
		}
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		session.removeAttribute("user");
		
		//浠嶴ervletContext涓彇鍑哄湪绾垮垪琛ㄩ泦鍚�
		List<User> list = (List<User>) getServletContext().getAttribute("onLineUserList");
		list.remove(user);
		
		response.sendRedirect(request.getContextPath() + "/login?method=getLoginPage");
	}
}
