package com.situ.student.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.situ.student.util.JDBCUtil;

public class InitServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("InitServlet.init()");
		ServletContext servletContext = getServletContext();
		JDBCUtil.init(servletContext);
		
		servletContext.setAttribute("count", 0);
	}
}
